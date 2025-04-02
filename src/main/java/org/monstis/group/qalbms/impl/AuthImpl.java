package org.monstis.group.qalbms.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Auth;
import org.monstis.group.qalbms.dto.Register;
import org.monstis.group.qalbms.dto.VerifyAuth;
import org.monstis.group.qalbms.exceptions.AuthException;
import org.monstis.group.qalbms.repository.AuthRepository;
import org.monstis.group.qalbms.service.AuthService;
import org.monstis.group.qalbms.service.EskizWebClient;
import org.monstis.group.qalbms.service.TokenCacherService;
import org.monstis.group.qalbms.utils.OtpGenerator;
import org.monstis.group.qalbms.utils.UzbekistanPhoneNumberValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthImpl implements AuthService {

    private final UzbekistanPhoneNumberValidator uzbPhoneNumberValidator;
    private final TokenCacherService tokenCacherService;
    private final EskizWebClient eskizWebClient;
    private final AuthRepository authRepository;
    private final OtpGenerator otpGenerator;

    @Value("${eskiz.username.token}")
    private String eskizUsernameToken;

    @Override
    public Mono<?> verifyOtp(@NotNull String msisdn, @NotNull String otp) {
        return authRepository
                .findByPhoneNumberAndOtpCode(msisdn, otp)
                .switchIfEmpty(Mono.error(new AuthException("Invalid OTP code")))
                .filter(auth -> !Instant.now().isAfter(auth.getCreatedAt().plusSeconds(60)))
                .switchIfEmpty(Mono.error(new AuthException("OTP code is expired")))
                .map(auth -> new VerifyAuth.Response("accessToken", "refreshToken"))
                .switchIfEmpty(Mono.error(new AuthException("unknown error while verifying otp code")));
    }

    @Override
    public Mono<?> registerPhone(@NotNull String msisdn) {
        if (!uzbPhoneNumberValidator.isValidUzbekistanPhoneNumber(msisdn)) {
            return Mono.error(new AuthException("Invalid phone number"));
        }

        String otp = otpGenerator.generateOTP();

        return getTokenFromLocalCache()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(eskizToken -> eskizWebClient.register(msisdn, eskizToken, otp))
                .filter(response -> response != null && !response.getStatus().equals("error"))
                .flatMap(response -> handleRegister(msisdn, otp))
                .switchIfEmpty(Mono.error(new Exception("Unknown error while registering phone " + msisdn)));
    }

    private Mono<Register.Response> handleRegister(@NotNull String msisdn, @NotNull String otp) {
        Auth auth = new Auth()
                .setPhoneNumber(msisdn)
                .setOtpCode(otp)
                .setCreatedAt(Instant.now());

        return authRepository
                .save(auth)
                .map(entity -> new Register.Response(msisdn, 60L))
                .flatMap(Mono::just);
    }

    @Override
    public Mono<Optional<String>> setTokenToLocalCache() {
        return eskizWebClient
                .getToken()
                .map(token -> tokenCacherService.setToken(eskizUsernameToken, token.getData().getToken()));
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
