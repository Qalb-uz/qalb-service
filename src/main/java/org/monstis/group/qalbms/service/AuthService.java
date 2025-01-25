package org.monstis.group.qalbms.service;


import org.monstis.group.qalbms.domain.Auth;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public interface AuthService {

    Mono<?>verifyOtp(String otp);

    Mono<?>registerPhone(String phone);

    Mono<Optional<String>>setTokenToLocalCache();

    Mono<Optional<String>>getTokenFromLocalCache();
}
