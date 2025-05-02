package org.monstis.group.qalbms.controller;


import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.monstis.group.qalbms.domain.PsychoIssueAnswer;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.monstis.group.qalbms.utils.TypedResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/send")
    public Mono<PsychoIssueAnswer> saveAnswers(@RequestBody List<PsychoIssueAnswer> topicsList, ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        topicsList.forEach(topicDTO -> topicDTO.setUsername(username));

        return applicationRepository.saveAll(topicsList)
                .next();
    }
    @PostMapping("delete-client-application")
    public Mono<Boolean>deleteClientApplication(@RequestParam("username") String username) {
        return applicationRepository.deleteByUsername(username);
    }

    @GetMapping("check-client-application")
    public Mono<?> checkClientApplication(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(7);
             return applicationRepository.findFirstByUsername(jwtUtil.extractUsername(token)).flatMap(psychoIssueAnswer -> {
                if(psychoIssueAnswer.isValid())
                {
                    return Mono.just(psychoIssueAnswer);
                }
           return Mono.just((psychoIssueAnswer));
       }).switchIfEmpty(Mono.error(new TypedResponseException(HttpStatus.NOT_FOUND,"RESUME", "Client resume not found")));

    }

//    @PutMapping("edit-client-application")
//    public Mono<?>editClientApplication(@RequestBody TopicDTO topicsFlux) {
//        return applicationRepository.save(topicsFlux);
//    }

} 
