package org.monstis.group.qalbms.impl;

import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.dto.SubtopicDTO;
import org.monstis.group.qalbms.dto.TopicDTO;
import org.monstis.group.qalbms.enums.Costs;

import org.monstis.group.qalbms.enums.PsychologicalApproach;
import org.monstis.group.qalbms.enums.PsychologicalIssue;
import org.monstis.group.qalbms.service.TheraphyService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class TheraphyImpl implements TheraphyService {

    @Override
    public Mono<Map<String,String>> getKeyWords() {
        Map<String, String> functionalities = Arrays.stream(PsychologicalIssue.values())
                .collect(Collectors.toMap(
                        issue -> issue.name(),  // Use the enum's name as the key
                        issue -> issue.getDescription("ru")  // Get the description in Russian
                ));
        return Mono.just(functionalities);
    }

    public Flux<TopicDTO> getApproach() {
        Map<String, List<PsychologicalApproach>> topicToSubtopics = Map.of(
                "Мое состояние", List.of(
                        PsychologicalApproach.STRESS_ANXIETY,
                        PsychologicalApproach.PANIC_ATTACKS,
                        PsychologicalApproach.OBSESSIVE_THOUGHTS,
                        PsychologicalApproach.MOOD_SWINGS,
                        PsychologicalApproach.SADNESS,
                        PsychologicalApproach.DEPRESSION,
                        PsychologicalApproach.SLEEP_PROBLEMS,
                        PsychologicalApproach.EMOTIONAL_BURNOUT,
                        PsychologicalApproach.GUILT_FEELINGS,
                        PsychologicalApproach.FAMILY_EXPECTATIONS,
                        PsychologicalApproach.LOW_SELF_ESTEEEM,
                        PsychologicalApproach.EMOTIONAL_DEPENDENCE,
                        PsychologicalApproach.LONELINESS,
                        PsychologicalApproach.APATHY,
                        PsychologicalApproach.EXPRESSING_EMOTIONS,
                        PsychologicalApproach.EMOTION_CONTROL

                ),
                "Работа", List.of(
                        PsychologicalApproach.RELATIONSHIP_CONFLICTS,
                        PsychologicalApproach.PARTNER_ACCEPTANCE,
                        PsychologicalApproach.DIFFERENT_VIEWS,
                        PsychologicalApproach.JEALOUSY,
                        PsychologicalApproach.FREEDOM_RESTRICTION,
                        PsychologicalApproach.CHEATING,
                        PsychologicalApproach.BREAKUP_DIVORCE,
                        PsychologicalApproach.TRUST_LOSS,
                        PsychologicalApproach.NO_INTIMACY,
                        PsychologicalApproach.CANT_FIND_PARTNER,
                        PsychologicalApproach.TOXIC_RELATIONSHIPS,
                        PsychologicalApproach.FEAR_INTIMACY,
                        PsychologicalApproach.VIOLENCE_ABUSE,
                        PsychologicalApproach.DISSATISFACTION,
                        PsychologicalApproach.RELATIVES_PRESSURE
                ),
                "Семейные отношения", List.of(
                        PsychologicalApproach.PARENT_CHILD_CONFLICT,
                        PsychologicalApproach.COMMUNICATION_PROBLEMS,
                        PsychologicalApproach.PARENTING_PROBLEMS,
                        PsychologicalApproach.RELATIVES_RELATIONSHIPS
                ),
                "Работа, карьера, учеба", List.of(
                        PsychologicalApproach.CAREER_CHOOSING_DIFFICULTIES,
                        PsychologicalApproach.WORK_BURNOUT,
                        PsychologicalApproach.NO_MOTIVATION,
                        PsychologicalApproach.PROCRASTINATION,
                        PsychologicalApproach.FAILURE_FEAR,
                        PsychologicalApproach.WORK_STRESS,
                        PsychologicalApproach.CONFLICTS,
                        PsychologicalApproach.CAREER_CRISIS
                ),
                "Жизненные изменения", List.of(
                        PsychologicalApproach.IMMIGRATION,
                        PsychologicalApproach.JOB_CHANGE,
                        PsychologicalApproach.STUDY,
                        PsychologicalApproach.CHILD_BIRTH,
                        PsychologicalApproach.MARRIAGE,
                        PsychologicalApproach.DIVORCE,
                        PsychologicalApproach.LOSS
                )
        );

        List<TopicDTO> topics = new ArrayList<>();
        int topicId = 0;

        for (Map.Entry<String, List<PsychologicalApproach>> entry : topicToSubtopics.entrySet()) {
            String topicTitle = entry.getKey();
            List<SubtopicDTO> subtopics = new ArrayList<>();

            int subId = 0;
            for (PsychologicalApproach approach : entry.getValue()) {
                subtopics.add(new SubtopicDTO(subId++, approach.getDescription("ru")));
            }

            topics.add(new TopicDTO(topicId++, topicTitle, subtopics));
        }

        return Flux.fromIterable(topics);
    }

    @Override
    public Mono<List<Map<String, String>>> getCosts() {
        List<Map<String, String>> costsList = Stream.of(Costs.values())
                .map(cost -> {
                    Map<String, String> data = new HashMap<>();
                    data.put("title", cost.name());
                    data.put("costsInSom", cost.getCostsINSom());
                    data.put("subtitle", cost.getSubtitle());
                    data.put("experience", cost.getExperience());
                    return data;
                })
                .collect(Collectors.toList());

        return Mono.just(costsList);
    }


}
