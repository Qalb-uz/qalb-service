package org.monstis.group.qalbms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.impl.PsychologistSearchService;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "search psychologs with  params api")
public class SearchController {


    private final PsychologistSearchService psychologistSearchService;

    public SearchController(PsychologistSearchService psychologistSearchService) {
        this.psychologistSearchService = psychologistSearchService;
    }


    @PostMapping("/search")
    @Operation(summary = "search for psychologs ", description = "REQUIRED_ROLES: <b></b>")
    public Mono<List<SearchHit<Psychologist>>> searchPsychologs(@RequestParam(required = false) String gender,
                                                                @RequestParam(required = false) String priceForSession,
                                                                @RequestParam(required = false) String psychologicalApproaches,
                                                                @RequestParam(required = false) String psychoIssues) {
        return psychologistSearchService.searchPsychologists(gender, priceForSession, psychologicalApproaches, psychoIssues);
    }
}
