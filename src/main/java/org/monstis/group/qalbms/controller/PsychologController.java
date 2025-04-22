package org.monstis.group.qalbms.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.ContentDTO;
import org.monstis.group.qalbms.dto.ContentForAllDTO;
import org.monstis.group.qalbms.dto.PsychologistDTO;
import org.monstis.group.qalbms.service.PsychologistService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "add pyschologs rest api")
public class PsychologController {

    private final PsychologistService psychologistService;

    public PsychologController(PsychologistService psychologistService) {
        this.psychologistService = psychologistService;
    }

    @PostMapping("/add/psychologs")
    @Operation(summary = "add psychologs", description = "REQUIRED_ROLES: <b></b>")
    public Mono<Psychologist>savePsychologs(@RequestBody PsychologistDTO psychologistDTO) {
        return psychologistService.addPsychologyist(psychologistDTO);

    }

    @GetMapping("/get/all-psychologs")
    @Operation(summary = "add psychologs", description = "REQUIRED_ROLES: <b></b>")
    public Mono<ContentDTO> getAllPsychologs( @RequestParam("size") Integer size, @RequestParam("key") Integer key) {
        return psychologistService.getAllPsychologyist(key, size);
    }



}
