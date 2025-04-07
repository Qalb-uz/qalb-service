package org.monstis.group.qalbms.controller;


import lombok.RequiredArgsConstructor;
import org.monstis.group.qalbms.domain.PsychoIssueAnswer;
import org.monstis.group.qalbms.dto.PsychoIssueAnswerDTO;
import org.monstis.group.qalbms.dto.SubtopicDTO;
import org.monstis.group.qalbms.dto.TopicDTO;
import org.monstis.group.qalbms.repository.PsychoIssueAnswerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class PsychoIssueController {

    private final PsychoIssueAnswerRepository answerRepository;

    @PostMapping("/answers")
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


    @GetMapping("/answers")
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

//    @GetMapping("check-client-application")
//    public Mono<TopicDTO> checkClientApplication() {
//        answerRepository.fi
//
//    }

} 
