package org.monstis.group.qalbms.impl;

import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Auth;
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
public class AuthImpl implements AuthService {
   private final UzbekistanPhoneNumberValidator uzbPhoneNumberValidator;
   private final EskizWebClient eskizWebClient;
   private final TokenCacherService tokenCacherService;
   private final OtpGenerator otpGenerator;
   private final AuthRepository authRepository;

   @Value("${eskiz.username.token}")
   private String eskizUsernameToken;

    public AuthImpl(UzbekistanPhoneNumberValidator uzbPhoneNumberValidator, EskizWebClient eskizWebClient, TokenCacherService tokenCacherService, OtpGenerator otpGenerator, AuthRepository authRepository) {
        this.uzbPhoneNumberValidator = uzbPhoneNumberValidator;
        this.eskizWebClient = eskizWebClient;
        this.tokenCacherService = tokenCacherService;
        this.otpGenerator = otpGenerator;
        this.authRepository = authRepository;
    }


    @Override
    public Mono<?> verifyOtp(String otp) {
        return authRepository.findByOtpCode(otp).flatMap(auth -> {
            if(!Instant.now().isAfter(auth.getCreatedAt().plusSeconds(60))){
                return Mono.just(auth);
            }else{
                return Mono.just(("OTP code is expired "));
            }
        });
    }

    @Override
    public Mono<?> registerPhone(String phone) {
        if(uzbPhoneNumberValidator.isValidUzbekistanPhoneNumber(phone)){
            Integer otp= Integer.valueOf(otpGenerator.generateOTP());
           return getTokenFromLocalCache()
                   .flatMap(cacheToken -> eskizWebClient.register(phone, cacheToken.get(),otp)
                           .flatMap(eskizResponse -> {
                               if(!eskizResponse.getStatus().equals("error")){
                                   Auth auth=new Auth();
                                   auth.setPhoneNumber(phone);
                                   auth.setOtpCode(String.valueOf(otp));
                                   auth.setCreatedAt(Instant.now());
                                   return authRepository.save(auth)
                                           .flatMap(Mono::just);
                               }
                               return Mono.empty();
                           }));
        }
        return Mono.just(("Invalid phone number"));
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
