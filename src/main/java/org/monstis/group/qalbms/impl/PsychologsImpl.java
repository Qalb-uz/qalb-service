package org.monstis.group.qalbms.impl;

import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.ElkaResponse;
import org.monstis.group.qalbms.dto.PsychologistDTO;
import org.monstis.group.qalbms.repository.ElasticSearchRepository;
import org.monstis.group.qalbms.service.PsychologistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PsychologsImpl implements PsychologistService {
    private final ElasticSearchRepository elasticSearchRepository;

    public PsychologsImpl(ElasticSearchRepository elasticSearchRepository) {
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @Override
    public Mono<String> addPsychologyist(PsychologistDTO psychologistDTO) {
        Psychologist psychologist = new Psychologist();
        psychologist.setAge(psychologistDTO.getAge());
        psychologist.setGender(psychologistDTO.getGender());
        psychologist.setPhoneNumber(psychologistDTO.getPhoneNumber());
        psychologist.setPriceForSession(psychologistDTO.getPriceForSession());
        psychologist.setFirstName(psychologistDTO.getFirstName());
        psychologist.setLastName(psychologistDTO.getLastName());
        psychologist.setPsychologicalApproaches(psychologistDTO.getPsychologicalApproaches());
        psychologist.setPsychoIssues(psychologistDTO.getPsychoIssues());

         return elasticSearchRepository.save(psychologist)
                .onErrorResume(e -> {
                    log.error("Error saving psychologist: {}", e.getMessage());
                    return Mono.error(new Error("Error  occurred in saving"));
                })
                .map(savedPsychologist -> "Successfully saved psychologist with ID: " + savedPsychologist.getId());


    }


    @Override
    public Flux<Psychologist> getAllPsychologyist() {
        return (elasticSearchRepository.findAll());
    }
}
