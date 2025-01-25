package org.monstis.group.qalbms.repository;


import org.monstis.group.qalbms.domain.Psychologist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


public interface ElasticSearchRepository extends ElasticsearchRepository<Psychologist, String> {
}