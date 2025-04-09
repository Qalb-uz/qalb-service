package org.monstis.group.qalbms.impl;

import org.monstis.group.qalbms.domain.Psychologist;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PsychologistSearchService {


    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    public PsychologistSearchService(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }

    public Mono<List<Psychologist>> searchPsychologists(String gender, String priceForSession, String psychoIssues) {

        Criteria criteria = new Criteria();

        if (gender != null) {
            criteria = criteria.and("gender").is(gender);
        }
        if (priceForSession != null) {
            criteria = criteria.and("priceForSession").is(priceForSession);
        }
        if (psychoIssues != null) {
            criteria = criteria.and("psychoIssues").is(psychoIssues);
        }

        Query query = new CriteriaQuery(criteria);

        PageRequest pageRequest = PageRequest.of(0, 10);
        query.setPageable(pageRequest);

        return reactiveElasticsearchTemplate.search(query, Psychologist.class)
                .map(SearchHit::getContent)
                .collectList();
    }

}
