package org.monstis.group.qalbms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.dto.SessionDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "search psychologs with  params api")
public class SessionController {

    @PostMapping("/create-session")
    public Mono<SessionDTO> createSession(@RequestParam ("session_time") String session) {
        log.info("Session time: {}", session);
        SessionDTO sessionDTO = new SessionDTO();
        return Mono.just(sessionDTO);
    }

}
