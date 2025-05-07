package org.monstis.group.qalbms.service;

import org.monstis.group.qalbms.dto.CheckClientResponseDTO;
import reactor.core.publisher.Mono;

public interface ApplicationService {

    Mono<CheckClientResponseDTO>checkApplication(String  username);
}
