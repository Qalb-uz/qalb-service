package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.Session;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SessionRepository extends R2dbcRepository<Session, String> {

    Mono<Session>findByTimeAndTherapistIdAndUsername(String time, String therapistId,String  username);
    Mono<Session> findByIdAndUsername(String id, String username);
    Flux<Session> findAllByUsername(String username);

}

