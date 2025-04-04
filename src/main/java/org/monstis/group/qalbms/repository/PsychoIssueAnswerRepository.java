package org.monstis.group.qalbms.repository;


import org.monstis.group.qalbms.domain.PsychoIssueAnswer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsychoIssueAnswerRepository extends ReactiveCrudRepository<PsychoIssueAnswer, Long> {
}
