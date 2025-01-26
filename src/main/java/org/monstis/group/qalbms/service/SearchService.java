package org.monstis.group.qalbms.service;

import org.monstis.group.qalbms.domain.Psychologist;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface SearchService {
    Flux<Psychologist>searchWithParams(String cost,String symptoms,String approach,String gender);



}
