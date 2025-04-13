package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.Client;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends R2dbcRepository<Client,Long> {

    Mono<Client> findByDeviceIdAndMsisdn(String deviceId, String msisdn);


}
