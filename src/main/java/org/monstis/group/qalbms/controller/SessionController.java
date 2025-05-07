package org.monstis.group.qalbms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.monstis.group.qalbms.domain.Session;
import org.monstis.group.qalbms.dto.GetAllSessionListResponseDTO;
import org.monstis.group.qalbms.dto.GetAllSessionResponseDTO;
import org.monstis.group.qalbms.dto.SessionRequestDTO;
import org.monstis.group.qalbms.dto.SessionResponseDto;
import org.monstis.group.qalbms.repository.SessionRepository;
import org.monstis.group.qalbms.service.CreateSessionService;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "search psychologs with  params api")
public class SessionController {
    private final JwtUtil jwtUtil;
    private final CreateSessionService createSessionService;
    private final SessionRepository sessionRepository;

    public SessionController(JwtUtil jwtUtil, CreateSessionService createSessionService, SessionRepository sessionRepository) {
        this.jwtUtil = jwtUtil;
        this.createSessionService = createSessionService;
        this.sessionRepository = sessionRepository;
    }

//todo check therpist time if that time is seleccted show error
    @PostMapping("/create-session")
    public Mono<SessionResponseDto> createSession(@RequestBody SessionRequestDTO session, ServerWebExchange serverWebExchange) {

        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username=jwtUtil.extractUsername(token);
        String deviceId = jwtUtil.extractDeviceId(token);
        log.info("Session time: {}", session);
        return createSessionService.createSession(session,username,deviceId);
    }
    @GetMapping("get-all-sessions")
    public Mono<GetAllSessionListResponseDTO>getClientAllSessions(@RequestParam("key")Integer key, @RequestParam("size")Integer size, ServerWebExchange serverWebExchange){
        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username=jwtUtil.extractUsername(token);
        return createSessionService.getAllSession(username,key,size)
                .doOnNext(session -> log.info("Session found: {}", session))
                .doOnError(e -> log.error("Error retrieving sessions: {}", e.getMessage()));

    }

}
