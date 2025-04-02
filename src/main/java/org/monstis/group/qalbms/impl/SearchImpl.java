package org.monstis.group.qalbms.impl;

import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.repository.ElasticSearchRepository;
import org.monstis.group.qalbms.service.SearchService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class SearchImpl implements SearchService {
    private final ElasticSearchRepository elasticSearchRepository;


    public SearchImpl(ElasticSearchRepository elasticSearchRepository) {
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @Override
    public Flux<Psychologist> searchWithParams(String cost, String symptoms, String approach, String gender) {
        return null;
    }


}
