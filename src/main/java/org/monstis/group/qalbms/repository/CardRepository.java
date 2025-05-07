package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.Card;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface CardRepository extends R2dbcRepository<Card, Long> {
    Mono<List<Card>> findAllByCardPhoneNumber(String cardPhoneNumber);

    Mono<Card>findById(Long cardId);

}
