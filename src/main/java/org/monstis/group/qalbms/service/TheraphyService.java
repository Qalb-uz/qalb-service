package org.monstis.group.qalbms.service;

import org.monstis.group.qalbms.dto.TopicDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public interface TheraphyService {

    Mono<Map<String,String>>getKeyWords();

    Flux<TopicDTO>getApproach();

    Mono<List<Map<String, String>>>getCosts();
}
