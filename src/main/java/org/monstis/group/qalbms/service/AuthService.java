package org.monstis.group.qalbms.service;


import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public interface AuthService {

    Mono<?> verifyOtp(@NotNull String msisdn, @NotNull String otp);

    Mono<?> registerPhone(@NotNull String msisdn);

    Mono<Optional<String>> setTokenToLocalCache();

    Mono<Optional<String>> getTokenFromLocalCache();
}
