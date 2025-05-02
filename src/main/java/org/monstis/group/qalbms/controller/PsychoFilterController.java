package org.monstis.group.qalbms.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.dto.TopicDTO;
import org.monstis.group.qalbms.service.TheraphyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "REST APIs")
public class PsychoFilterController {

    private final TheraphyService theraphyService;

    public PsychoFilterController(TheraphyService theraphyService) {
        this.theraphyService = theraphyService;
    }


    @GetMapping("/symptoms")
    @Operation(summary = "psycho symptoms", description = "REQUIRED_ROLES: <b></b>")
    public Mono<Map<String,String>> getKeywords() {
        return theraphyService.getKeyWords();
    }

    @GetMapping("/approach")
    @Operation(summary = "approach to psycho symptoms", description = "REQUIRED_ROLES: <b></b>")
    public Flux<TopicDTO> getApproaches() {
        return theraphyService.getApproach();
    }

    @GetMapping("/cost")
    @Operation(summary = "cost for session", description = "REQUIRED_ROLES: <b></b>")
    public Mono<List<Map<String, String>>> getCost() {
        return theraphyService.getCosts();
    }
}
