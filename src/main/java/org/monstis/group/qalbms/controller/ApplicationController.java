package org.monstis.group.qalbms.controller;


import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
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
    public Mono<Void> saveAnswers(@RequestBody Flux<TopicDTO> topicsFlux) {
        return topicsFlux
                .flatMap(topic -> {
                    List<PsychoIssueAnswer> answers = new ArrayList<>();
                    for (SubtopicDTO sub : topic.getSubtopics()) {
                        answers.add(new PsychoIssueAnswer(
                                topic.getId(),
                                topic.getTitle(),
                                sub.getAdditional(),
                                sub.getTitle()
                        ));
                    }
                    return answerRepository.saveAll(answers).then();
                })
                .then();
    }


    @GetMapping("/get")
    public Flux<TopicDTO> getAllAnswers() {
        return answerRepository.findAll()
                .collectList()
                .flatMapMany(answers -> {
                    Map<Integer, TopicDTO> topicMap = new LinkedHashMap<>();

                    for (PsychoIssueAnswer answer : answers) {
                        topicMap.computeIfAbsent(answer.getTopicId(), id ->
                                new TopicDTO(id, answer.getTopicTitle(), new ArrayList<>())
                        );

                        SubtopicDTO subtopic = new SubtopicDTO(
                                answer.getSubtopicTitle()
                        );

                        // avoid duplicates
                        List<SubtopicDTO> subtopics = topicMap.get(answer.getTopicId()).getSubtopics();

                    }

                    return Flux.fromIterable(topicMap.values());
                });
    }

    @GetMapping("check-client-application")
    public Mono<PsychoIssueAnswer> checkClientApplication(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(7);
       return answerRepository.findByUsername(jwtUtil.getUsername(token)).switchIfEmpty(Mono.error(new Error("Client Application Not Found")));

    }

} 
