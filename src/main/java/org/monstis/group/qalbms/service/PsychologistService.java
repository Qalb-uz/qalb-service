package org.monstis.group.qalbms.service;


import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.CalendarContentDTO;
import org.monstis.group.qalbms.dto.ContentDTO;
import org.monstis.group.qalbms.dto.PsychologistDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface PsychologistService {

    Mono<Psychologist>addPsychologyist(PsychologistDTO psychologistDTO);

    Mono<ContentDTO>getAllPsychologyist(Integer size, Integer key);

    Mono<List<CalendarContentDTO>> findByPsychologId(String psychologId);
}
