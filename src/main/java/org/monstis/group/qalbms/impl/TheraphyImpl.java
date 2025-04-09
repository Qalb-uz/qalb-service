package org.monstis.group.qalbms.impl;

import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.enums.Costs;

import org.monstis.group.qalbms.enums.PsychologicalApproach;
import org.monstis.group.qalbms.enums.PsychologicalIssue;
import org.monstis.group.qalbms.service.TheraphyService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public Mono<Map<String, String>> getApproach() {
        Map<String, String> approach = Arrays.stream(PsychologicalApproach.values())
                .collect(Collectors.toMap(
                        issue -> issue.name(),  // Use the enum's name as the key
                        issue -> issue.getDescription("ru")  // Get the description in Russian
                ));
        return Mono.just(approach);
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
