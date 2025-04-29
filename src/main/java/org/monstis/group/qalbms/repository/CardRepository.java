package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.Card;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CardRepository extends R2dbcRepository<Card, Long> {
    Mono<Card> findByCardPhoneNumber(String cardPhoneNumber);

}
