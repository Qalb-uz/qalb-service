package org.monstis.group.qalbms.repository;


import org.monstis.group.qalbms.domain.PsychoIssueAnswer;
import org.monstis.group.qalbms.dto.TopicDTO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ApplicationRepository extends ReactiveCrudRepository<PsychoIssueAnswer, Long> {

    Mono<PsychoIssueAnswer> findFirstByUsername(String username);

    Mono<PsychoIssueAnswer>save(TopicDTO topicDTO);

    Mono<Boolean>deleteByUsername(String username);
}
