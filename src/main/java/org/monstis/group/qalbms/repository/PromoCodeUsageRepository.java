package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.PromoCodeUsage;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PromoCodeUsageRepository extends R2dbcRepository<PromoCodeUsage, Long> {

    Mono<Long> countByPromoCodeIdAndUsername(Long promoCodeId, String username);
    Mono<Long>countByUsername(String username);

}
