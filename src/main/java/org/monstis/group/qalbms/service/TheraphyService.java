package org.monstis.group.qalbms.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public interface TheraphyService {

    Mono<Map<String, String>> getKeyWords();

    Mono<Map<String, String>> getApproach();

    Mono<Map<String, String>> getCosts();
}
