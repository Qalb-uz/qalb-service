package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.Card;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface CardRepository extends R2dbcRepository<Card, Long> {
    Flux<Card> findAllByCardPhoneNumber(String cardPhoneNumber);

    Mono<Card>findByPanAndCardPhoneNumber(String pan, String cardPhoneNumber);

    @Query("SELECT * FROM cards WHERE card_phone_number = :cardPhoneNumber ORDER BY created_at DESC LIMIT 1")
    Mono<Card> findLastAddedByCardPhoneNumber(@Param("cardPhoneNumber") String cardPhoneNumber);

    Mono<Card>findById(Long cardId);

}
