package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.Auth;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthRepository extends R2dbcRepository<Auth,Long> {

    Mono<Auth>findByOtpCodeAndPhoneNumber(String otpCode,String phone);

}
