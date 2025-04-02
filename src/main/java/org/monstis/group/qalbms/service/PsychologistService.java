package org.monstis.group.qalbms.service;


import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.PsychologistDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface PsychologistService {

    Mono<String> addPsychologyist(PsychologistDTO psychologistDTO);

    Flux<Psychologist> getAllPsychologyist();
}
