package org.monstis.group.qalbms.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.monstis.group.qalbms.domain.PsychoIssueAnswer;
import org.monstis.group.qalbms.dto.TopicDTO;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
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



    @GetMapping("check-client-application")
    public Mono<?> checkClientApplication(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(7);
             return applicationRepository.findFirstByUsername(jwtUtil.extractUsername(token)).flatMap(psychoIssueAnswer -> {
                if(psychoIssueAnswer.isValid())
                {
                    return Mono.just("Client application valid");
                }
           return Mono.just((psychoIssueAnswer));
       }).switchIfEmpty(Mono.error(new Error("Client Application Not Found")));

    }

    @PutMapping("edit-client-application")
    public Mono<?>editClientApplication(@RequestBody TopicDTO topicsFlux) {
        return applicationRepository.save(topicsFlux);
    }

} 
