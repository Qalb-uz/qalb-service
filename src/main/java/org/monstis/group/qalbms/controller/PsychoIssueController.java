package org.monstis.group.qalbms.controller;


import lombok.RequiredArgsConstructor;
import org.monstis.group.qalbms.domain.PsychoIssueAnswer;
import org.monstis.group.qalbms.dto.PsychoIssueAnswerDTO;
import org.monstis.group.qalbms.repository.PsychoIssueAnswerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class PsychoIssueController {

    private final PsychoIssueAnswerRepository answerRepository;

    @PostMapping("/answers")
    public Mono<ResponseEntity<String>> saveAnswers(@RequestBody PsychoIssueAnswerDTO dto) {
        PsychoIssueAnswer entity = new PsychoIssueAnswer(dto);
        return answerRepository.save(entity)
                .thenReturn(ResponseEntity.ok("Answers submitted successfully."));
    }

    @GetMapping("/answers")
    public Flux<PsychoIssueAnswer> getAllAnswers() {
        return answerRepository.findAll();
    }
} 
