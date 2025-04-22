package org.monstis.group.qalbms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.monstis.group.qalbms.dto.CalendarContentDTO;
import org.monstis.group.qalbms.dto.ContentDTO;
import org.monstis.group.qalbms.impl.PsychologistSearchService;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.service.PsychologistService;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
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
    @Operation(summary = "search for psychologists", description = "REQUIRED_ROLES: <b></b>")
    public Mono<ContentDTO> searchPsychologists(
            @RequestParam("apply_filter") Boolean filter,
            ServerWebExchange exchange,
            @RequestParam("size") Integer size,@RequestParam("key") Integer key
    ) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        if (!filter) {
            return psychologistService.getAllPsychologyist(size, key);
        }

        return applicationRepository.findFirstByUsername(username)
                .flatMap(psychoIssueAnswer ->
                        psychologistSearchService.searchPsychologists(
                                psychoIssueAnswer.getPsychoGender(),
                                psychoIssueAnswer.getCost(),
                                psychoIssueAnswer.getSubtopics(),
                                size,
                                String.valueOf(key)
                        )
                );
    }

    @GetMapping("/get-session-dates")
    @Operation(summary = "search for psychologists", description = "REQUIRED_ROLES: <b></b>")
    public Mono<CalendarContentDTO> getCalendarDates(
            ServerWebExchange exchange,
            @RequestParam("psychologist_id") String psychologistId
    ) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        return psychologistService.findByPsychologId(psychologistId);

    }



}
