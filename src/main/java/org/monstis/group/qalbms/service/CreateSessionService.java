package org.monstis.group.qalbms.service;

import org.monstis.group.qalbms.domain.Session;
import org.monstis.group.qalbms.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CreateSessionService {

    Mono<SessionResponseDto>createSession(SessionRequestDTO session, String username, String deviceId);

    Mono<GetAllSessionListResponseDTO> getAllSession(String username, Integer key, Integer size);

}
