package org.monstis.group.qalbms.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.monstis.group.qalbms.domain.Auth;
import org.monstis.group.qalbms.domain.Client;
import org.monstis.group.qalbms.dto.OtpResponse;
import org.monstis.group.qalbms.dto.TokenResponse;
import org.monstis.group.qalbms.dto.VerifyDTO;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.repository.AuthRepository;
import org.monstis.group.qalbms.repository.ClientRepository;
import org.monstis.group.qalbms.service.AuthService;
import org.monstis.group.qalbms.service.EskizWebClient;
import org.monstis.group.qalbms.service.KeycloakService;
import org.monstis.group.qalbms.service.TokenCacherService;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.monstis.group.qalbms.utils.OtpGenerator;
import org.monstis.group.qalbms.utils.TypedResponseException;
import org.monstis.group.qalbms.utils.UzbekistanPhoneNumberValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class AuthImpl implements AuthService {
   private final UzbekistanPhoneNumberValidator uzbPhoneNumberValidator;
   private final EskizWebClient eskizWebClient;
   private final TokenCacherService tokenCacherService;
   private final OtpGenerator otpGenerator;
   private final AuthRepository authRepository;
   private final KeycloakService keycloakService;
   private final ApplicationRepository applicationRepository;
   private final JwtUtil jwtUtil;
   private final ClientRepository clientRepository;

   @Value("${eskiz.username.token}")
   private String eskizUsernameToken;

    public AuthImpl(UzbekistanPhoneNumberValidator uzbPhoneNumberValidator, EskizWebClient eskizWebClient, TokenCacherService tokenCacherService, OtpGenerator otpGenerator, AuthRepository authRepository, KeycloakService keycloakService, ApplicationRepository applicationRepository, JwtUtil jwtUtil, ClientRepository clientRepository) {
        this.uzbPhoneNumberValidator = uzbPhoneNumberValidator;
        this.eskizWebClient = eskizWebClient;
        this.tokenCacherService = tokenCacherService;
        this.otpGenerator = otpGenerator;
        this.authRepository = authRepository;
        this.keycloakService = keycloakService;
        this.applicationRepository = applicationRepository;
        this.jwtUtil = jwtUtil;
        this.clientRepository = clientRepository;
    }


    @Override
    public Mono<VerifyDTO> verifyOtp(String otp, String phone, String deviceName, String deviceId) {
        log.info("Verifying OTP for phone: {}", phone);  // Added log
        return authRepository.findByOtpCodeAndPhoneNumber(DigestUtils.sha256Hex(otp), phone).flatMap(auth -> {
            if (!Instant.now().isAfter(auth.getCreatedAt().plusSeconds(60))) {
                log.info("OTP is valid. Proceeding to token generation.");  // Added log
                auth.setVerified(true);
                TokenResponse tokenResponse = jwtUtil.generateTokens(phone, deviceId, deviceName);
                VerifyDTO verifyDTO = new VerifyDTO();
                VerifyDTO.DeviceDto deviceDto = new VerifyDTO.DeviceDto();
                return authRepository.save(auth).then(clientRepository.findByDeviceIdAndMsisdn(deviceId, phone).flatMap(client -> {
                    log.info("Client found for deviceId: {} and phone: {}", deviceId, phone);  // Added log

                    deviceDto.setId(deviceId);
                    deviceDto.setName(deviceName);
                    deviceDto.setAccessToken(tokenResponse.accessToken());
                    deviceDto.setRefreshToken(tokenResponse.refreshToken());
                    deviceDto.setFirstLoginAt(Instant.now());
                    verifyDTO.setDevice(deviceDto);

                    VerifyDTO.UserDto userDTO = new VerifyDTO.UserDto();
                    userDTO.setMsisdn(phone);
                    userDTO.setName(client.getFirstName());
                    userDTO.setId(client.getId());
                    verifyDTO.setUser(userDTO);
                    verifyDTO.setResumeStatus(client.getResumeStatus());

                    log.info("Returning verifyDTO with valid resume status: {}", verifyDTO);  // Added log
                    return Mono.just(verifyDTO);
                })).switchIfEmpty(Mono.defer(() -> {
                    log.info("No client found for deviceId: {} and phone: {}. Creating new client.", deviceId, phone);  // Added log
                    deviceDto.setId(deviceName);
                    deviceDto.setName(deviceId);
                    deviceDto.setAccessToken(tokenResponse.accessToken());
                    deviceDto.setRefreshToken(tokenResponse.refreshToken());
                    deviceDto.setFirstLoginAt(Instant.now());
                    verifyDTO.setDevice(deviceDto);


                    VerifyDTO.UserDto userDTO = new VerifyDTO.UserDto();
                    userDTO.setMsisdn(phone);
                    userDTO.setName(deviceName);
                    userDTO.setId(Long.valueOf(UUID.randomUUID().toString()));  // Generate a new ID for the user

                    verifyDTO.setUser(userDTO);
                    verifyDTO.setResumeStatus(String.valueOf(VerifyDTO.ResumeStatus.INVALID));

                    log.info("Returning verifyDTO with INVALID resume status: {}", verifyDTO);  // Added log
                    return Mono.just(verifyDTO);
                }));
            } else {
                log.error("OTP expired or invalid");  // Added log
                return Mono.error(new TypedResponseException(HttpStatus.BAD_REQUEST, "OTP", "OTP expired or invalid. Please request a new OTP."));
            }
        });
    }


    @Override
    public Mono<ResponseEntity<OtpResponse>> registerPhone(String phone,String deviceName,String deviceId) {
        log.info("Registering phone: {} with deviceId: {}", phone, deviceId);  // Added log
        if (!uzbPhoneNumberValidator.isValidUzbekistanPhoneNumber(phone)) {
            return Mono.error(new TypedResponseException(HttpStatus.BAD_REQUEST, "AUTH", "Invalid phone number"));
        }

        String otp = generateSecureOTP(); // Secure OTP generation

        return getTokenFromLocalCache()
                .flatMap(cacheToken -> eskizWebClient.register(phone, cacheToken.get(), Integer.parseInt(otp))
                        .flatMap(eskizResponse -> {
                            if (!"error".equals(eskizResponse.getStatus())) {
                                Auth auth = new Auth();

                                auth.setPhoneNumber(phone);
                                auth.setOtpCode(hashOTP(otp)); // Hash OTP before storing
                                auth.setCreatedAt(Instant.now());
                                auth.setDeviceId(deviceId);
                                return clientRepository.findByDeviceIdAndMsisdn(deviceId, phone).switchIfEmpty(Mono.defer(() -> {
                                    log.info("No client found for deviceId: {} and phone: {}. Creating new client.", deviceId, phone);  // Added log
                                    Client client = new Client();
                                    client.setMsisdn(phone);
                                    client.setDeviceName(deviceName);
                                    client.setDeviceId(deviceId);
                                    client.setFirstLoginAt(Instant.now());
                                    client.setResumeStatus(String.valueOf(VerifyDTO.ResumeStatus.INVALID));
                                    return clientRepository.save(client);
                                })).flatMap(exClient -> {
                                    exClient.setMsisdn(phone);
                                    exClient.setDeviceName(deviceName);
                                    exClient.setDeviceId(deviceId);
                                    exClient.setResumeStatus(String.valueOf(VerifyDTO.ResumeStatus.INVALID));
                                    exClient.setFirstLoginAt(Instant.now());
                                    return clientRepository.save(exClient).flatMap(client1 -> authRepository.save(auth)
                                            .flatMap(auth1 -> Mono.just(ResponseEntity.ok(new OtpResponse("OTP sent successfully", phone)))));
                                });
                            }
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OtpResponse("OTP code expired", phone)));
                        })
                );
    }




    private String generateSecureOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }


    private String hashOTP(String otp) {
        return DigestUtils.sha256Hex(otp);
    }



    @Override
    public Mono<Optional<String>>setTokenToLocalCache() {
        return  eskizWebClient.getToken()
                .flatMap(token ->
                        Mono.just(tokenCacherService.setToken(eskizUsernameToken, token.getData().getToken())));
    }

    @Override
    public Mono<Optional<String>> getTokenFromLocalCache() {
        if (tokenCacherService.getToken(eskizUsernameToken).isPresent()) {
            return Mono.just(tokenCacherService.getToken(eskizUsernameToken));
        } else {
            return setTokenToLocalCache();
        }
    }

}
