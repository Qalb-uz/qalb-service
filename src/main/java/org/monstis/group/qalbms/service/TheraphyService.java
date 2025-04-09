package org.monstis.group.qalbms.service;

import org.monstis.group.qalbms.enums.Costs;
import org.monstis.group.qalbms.enums.PsychologicalIssue;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public interface TheraphyService {

    Mono<Map<String,String>>getKeyWords();

    Mono<Map<String,String>>getApproach();

    Mono<List<Map<String, String>>>getCosts();
}
