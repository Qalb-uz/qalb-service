package org.monstis.group.qalbms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.PagenationData;
import org.monstis.group.qalbms.dto.PsychologistDTO;
import org.monstis.group.qalbms.dto.SearchResultDTO;
import org.monstis.group.qalbms.impl.PsychologistSearchService;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.service.PsychologistService;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "search psychologs with  params api")
public class SearchController {


    private final PsychologistSearchService psychologistSearchService;
    private final PsychologistService psychologistService;
    private final ApplicationRepository applicationRepository;
    private final JwtUtil jwtUtil;

    public SearchController(PsychologistSearchService psychologistSearchService, PsychologistService psychologistService, ApplicationRepository applicationRepository, JwtUtil jwtUtil) {
        this.psychologistSearchService = psychologistSearchService;
        this.psychologistService = psychologistService;
        this.applicationRepository = applicationRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/search")
    @Operation(summary = "search for psychologs", description = "REQUIRED_ROLES: <b></b>")
    public Flux<?> searchPsychologs(@RequestParam("apply_filter") Boolean filter, ServerWebExchange exchange, @RequestBody PagenationData pageData) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        if (!filter) {
            return psychologistService.getAllPsychologyist(); // already returns Flux
        }
        return applicationRepository.findFirstByUsername(username)
                .flatMapMany(psychoIssueAnswer ->
                        psychologistSearchService.searchPsychologists(
                                psychoIssueAnswer.getPsychoGender(),
                                psychoIssueAnswer.getCost(),
                                psychoIssueAnswer.getSubtopics(),
                                pageData.getSize(),
                                pageData.getKey()
                        )
                );
    }


}
