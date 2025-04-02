package org.monstis.group.qalbms.service;


import org.monstis.group.qalbms.domain.Auth;
import org.monstis.group.qalbms.dto.OtpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public interface AuthService {

    Mono<?>verifyOtp(String otp,String phone);

    Mono<ResponseEntity<OtpResponse>>registerPhone(String phone);

    Mono<Optional<String>>setTokenToLocalCache();

    Mono<Optional<String>>getTokenFromLocalCache();
}
