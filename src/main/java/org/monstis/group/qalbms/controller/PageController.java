package org.monstis.group.qalbms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.monstis.group.qalbms.domain.PsychoIssueAnswer;
import org.monstis.group.qalbms.dto.PageOneDTO;
import org.monstis.group.qalbms.dto.PageThreeDTO;
import org.monstis.group.qalbms.dto.PageTwoDTO;
import org.monstis.group.qalbms.enums.Costs;
import org.monstis.group.qalbms.enums.SessionFor;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "REST APIs")
public class PageController {
    private final JwtUtil jwtUtil;
    private final ApplicationRepository applicationRepository;
    private final ObjectMapper objectMapper;

    public PageController(JwtUtil jwtUtil, ApplicationRepository applicationRepository, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.applicationRepository = applicationRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/page-one")
    public Mono<PsychoIssueAnswer> pageOne(@RequestBody PageOneDTO pageOneDTO, ServerWebExchange exchange) {
        log.info("Received request for page one with data: {}", pageOneDTO);
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        ;
        String username = jwtUtil.extractUsername(token);
        return applicationRepository.findFirstByUsername(username)
                .flatMap(psychoIssueAnswer -> {
                    log.info("Found existing PsychoIssueAnswer for username: {}", psychoIssueAnswer);
                    psychoIssueAnswer.setAge(pageOneDTO.getAge());
                    psychoIssueAnswer.setFirstName(pageOneDTO.getFirstName());
                    psychoIssueAnswer.setTheraphyLanguage(pageOneDTO.getLang().name());
                    psychoIssueAnswer.setUsername(username);

                    return applicationRepository.save(psychoIssueAnswer);
                }).switchIfEmpty(Mono.defer(() -> {
                    log.info("Creating new PsychoIssueAnswer for username: {}", username);
                    PsychoIssueAnswer psychoIssueAnswer = new PsychoIssueAnswer();
                    psychoIssueAnswer.setAge(pageOneDTO.getAge());
                    psychoIssueAnswer.setFirstName(pageOneDTO.getFirstName());
                    psychoIssueAnswer.setUsername(username);
                    psychoIssueAnswer.setTheraphyLanguage(pageOneDTO.getLang().name());

                    return applicationRepository.save(psychoIssueAnswer);
                }))
                .doOnError(error -> log.error("Error saving page one data: {}", error.getMessage()))
                .onErrorResume(error -> Mono.empty());
    }

    @PostMapping("/page-two")
    public Mono<PsychoIssueAnswer> pageTwo(@RequestBody PageTwoDTO pageTwoDTO, ServerWebExchange exchange) {
        log.info("Received request for page two with data: {}", pageTwoDTO);
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        ;
        String username = jwtUtil.extractUsername(token);
        return applicationRepository.findFirstByUsername(username)
                .flatMap(psychoIssueAnswer -> {
                    log.info("Found existing PsychoIssueAnswer for username: {}", psychoIssueAnswer);
                    psychoIssueAnswer.setPsychoGender(pageTwoDTO.getGender().name());
                    psychoIssueAnswer.setSessionFor(pageTwoDTO.getSessionFor().name());
                    return applicationRepository.save(psychoIssueAnswer);
                })
                .doOnError(error -> log.error("Error saving page one data: {}", error.getMessage()))
                .onErrorResume(error -> Mono.empty());
    }

    @PostMapping("/page-three-myself")
    public Mono<PsychoIssueAnswer> pageThreeMyself(@RequestBody List<PageThreeDTO> pageThreeDTOList, ServerWebExchange exchange) {
        log.info("Received request for page three with data: {}", pageThreeDTOList);

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        return applicationRepository.findFirstByUsername(username)
                .flatMap(psychoIssueAnswer -> {
                    try {
                        // Convert the whole topic list into JSON
                        String json = objectMapper.writeValueAsString(pageThreeDTOList);
                        psychoIssueAnswer.setTitle(pageThreeDTOList.get(0).getTitle());
                        psychoIssueAnswer.setSessionFor(SessionFor.MYSELF.name());
                        psychoIssueAnswer.setSubtopics((json));

                        return applicationRepository.save(psychoIssueAnswer);
                    } catch (JsonProcessingException e) {
                        log.error("Error converting to JSON: {}", e.getMessage());
                        return Mono.error(e);
                    }
                })
                .doOnError(error -> log.error("Error saving page three data: {}", error.getMessage()))
                .onErrorResume(error -> Mono.empty());
    }
    @PostMapping("/page-three-couple")
    public Mono<PsychoIssueAnswer> pageThreeCouple(@RequestBody List<PageThreeDTO> pageThreeDTOList, ServerWebExchange exchange) {
        log.info("Received request for page three with data: {}", pageThreeDTOList);

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        return applicationRepository.findFirstByUsername(username)
                .flatMap(psychoIssueAnswer -> {
                    try {
                        // Convert the whole topic list into JSON
                        String json = objectMapper.writeValueAsString(pageThreeDTOList);
                        psychoIssueAnswer.setTitle(pageThreeDTOList.get(0).getTitle());
                        psychoIssueAnswer.setSessionFor(SessionFor.COUPLE.name());
                        psychoIssueAnswer.setSubtopics((json));

                        return applicationRepository.save(psychoIssueAnswer);
                    } catch (JsonProcessingException e) {
                        log.error("Error converting to JSON: {}", e.getMessage());
                        return Mono.error(e);
                    }
                })
                .doOnError(error -> log.error("Error saving page three data: {}", error.getMessage()))
                .onErrorResume(error -> Mono.empty());
    }
    @PostMapping("/page-four")
    public Mono<PsychoIssueAnswer> pageFour(@RequestParam("title") String title, ServerWebExchange exchange) {

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        return applicationRepository.findFirstByUsername(username)
                .flatMap(psychoIssueAnswer -> {
                    String sum= Costs.getByName(title).getCostsINSom();
                    psychoIssueAnswer.setCost(sum);
                    return applicationRepository.save(psychoIssueAnswer);
                });



    }
}
