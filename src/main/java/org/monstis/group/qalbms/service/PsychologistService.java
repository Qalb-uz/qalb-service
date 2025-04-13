package org.monstis.group.qalbms.service;


import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.ElkaResponse;
import org.monstis.group.qalbms.dto.PsychologistDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface PsychologistService {

    Mono<Psychologist>addPsychologyist(PsychologistDTO psychologistDTO);

    Flux<PsychologistDTO>getAllPsychologyist();
}
