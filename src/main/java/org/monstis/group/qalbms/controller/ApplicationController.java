package org.monstis.group.qalbms.controller;


import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.monstis.group.qalbms.domain.PsychoIssueAnswer;
import org.monstis.group.qalbms.dto.SubtopicDTO;
import org.monstis.group.qalbms.dto.TopicDTO;
import org.monstis.group.qalbms.repository.PsychoIssueAnswerRepository;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final PsychoIssueAnswerRepository answerRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/send")
    public Flux<PsychoIssueAnswer> saveAnswers(@RequestBody Flux<TopicDTO> topicsFlux,ServerWebExchange exchange) {
        return topicsFlux
                .flatMap(topic -> {
                    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                    String token = authHeader.substring(7);
                   String username=jwtUtil.extractUsername(token);
                    List<PsychoIssueAnswer> answers = new ArrayList<>();
                    for (SubtopicDTO sub : topic.getSubtopics()) {
                        answers.add(new PsychoIssueAnswer(
                                topic.getTitle(),
                                sub.getAdditional(),
                                sub.getTitle(),
                                username
                        ));
                    }
                    return answerRepository.saveAll(answers).flatMap(Flux::just);
                });

    }


    @GetMapping("check-client-application")
    public Mono<PsychoIssueAnswer> checkClientApplication(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(7);
       return answerRepository.findFirstByUsername(jwtUtil.extractUsername(token)).switchIfEmpty(Mono.error(new Error("Client Application Not Found")));

    }

} 
