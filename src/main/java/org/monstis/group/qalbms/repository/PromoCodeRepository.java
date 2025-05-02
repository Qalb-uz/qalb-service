package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.PromoCode;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PromoCodeRepository extends R2dbcRepository<PromoCode, Long> {

    Mono<PromoCode> findByCode(String promoCode);



}
