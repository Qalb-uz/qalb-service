package org.monstis.group.qalbms.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.monstis.group.qalbms.domain.Auth;
import org.monstis.group.qalbms.dto.OtpResponse;
import org.monstis.group.qalbms.dto.TokenResponse;
import org.monstis.group.qalbms.repository.AuthRepository;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.service.AuthService;
import org.monstis.group.qalbms.service.EskizWebClient;
import org.monstis.group.qalbms.service.KeycloakService;
import org.monstis.group.qalbms.service.TokenCacherService;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.monstis.group.qalbms.utils.OtpGenerator;
import org.monstis.group.qalbms.utils.UzbekistanPhoneNumberValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;

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

   @Value("${eskiz.username.token}")
   private String eskizUsernameToken;

    public AuthImpl(UzbekistanPhoneNumberValidator uzbPhoneNumberValidator, EskizWebClient eskizWebClient, TokenCacherService tokenCacherService, OtpGenerator otpGenerator, AuthRepository authRepository, KeycloakService keycloakService, ApplicationRepository applicationRepository, JwtUtil jwtUtil) {
        this.uzbPhoneNumberValidator = uzbPhoneNumberValidator;
        this.eskizWebClient = eskizWebClient;
        this.tokenCacherService = tokenCacherService;
        this.otpGenerator = otpGenerator;
        this.authRepository = authRepository;
        this.keycloakService = keycloakService;
        this.applicationRepository = applicationRepository;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Mono<TokenResponse> verifyOtp(String otp, String phone) {
        return authRepository.findByOtpCodeAndPhoneNumber(DigestUtils.sha256Hex(otp),phone).flatMap(auth -> {
            if(!Instant.now().isAfter(auth.getCreatedAt().plusSeconds(60))){
            JwtUtil jwtUtil1 = new JwtUtil();
            TokenResponse tokenResponse=new TokenResponse(jwtUtil1.generateToken(auth.getPhoneNumber()),3600l,null,null);
                return Mono.just(tokenResponse);
            }else{
                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expired"));
            }
        });
    }
//    public Mono<TokenResponse> authenticate(String otp,String phone) {
//        log.info(" Process get Token for {}: ", phone);
//        Keycloak keycloak = KeycloakBuilder.builder().serverUrl("http://23.239.18.240:9999").realm("qalb-project").clientId("qalb_client")
//                .clientSecret("P9YCw0ovEEWIfpkyL0BL3pTESCZ6vawQ").username("998997960298").password("test")
//                .grantType(OAuth2Constants.PASSWORD).build();
//
//        String accessToken = keycloak.tokenManager().getAccessToken().getToken();
//        Long expiresIn = keycloak.tokenManager().getAccessToken().getExpiresIn();
//        String refreshToken = keycloak.tokenManager().getAccessToken().getRefreshToken();
//        Long refreshExpiresIn = keycloak.tokenManager().getAccessToken().getRefreshExpiresIn();
//        TokenResponse tokenResponse = new TokenResponse(accessToken, expiresIn, refreshToken, refreshExpiresIn);
//
//        return Mono.just(tokenResponse);
//    }

    @Override
    public Mono<ResponseEntity<OtpResponse>> registerPhone(String phone) {
        if (!uzbPhoneNumberValidator.isValidUzbekistanPhoneNumber(phone)) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number"));
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

                                return authRepository.save(auth)
                                        .then(Mono.just(ResponseEntity.ok(new OtpResponse("OTP sent successfully", phone))));
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
