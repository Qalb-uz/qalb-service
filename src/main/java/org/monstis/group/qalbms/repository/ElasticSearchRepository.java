package org.monstis.group.qalbms.repository;


import org.monstis.group.qalbms.domain.Psychologist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@EnableReactiveElasticsearchRepositories
public interface ElasticSearchRepository extends ReactiveElasticsearchRepository<Psychologist, String> {

    Mono<Psychologist> findByPhoneNumber(String phoneNumber);



}