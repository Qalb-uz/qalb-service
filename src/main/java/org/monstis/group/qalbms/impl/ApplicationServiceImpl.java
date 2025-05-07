package org.monstis.group.qalbms.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.dto.CheckClientResponseDTO;
import org.monstis.group.qalbms.dto.CheckClientSubTopicDTO;
import org.monstis.group.qalbms.dto.TopicDTO;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.service.ApplicationService;
import org.monstis.group.qalbms.utils.TypedResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    CheckClientResponseDTO checkClientResponseDTO = new CheckClientResponseDTO();
    List<TopicDTO> topics = null;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Mono<CheckClientResponseDTO> checkApplication(String username) {
        return applicationRepository.findFirstByUsername(username)
                .flatMap(psychoIssueAnswer -> {
                    CheckClientResponseDTO responseDTO = new CheckClientResponseDTO();
                    responseDTO.setId(psychoIssueAnswer.getId());
                    responseDTO.setFirstName(psychoIssueAnswer.getFirstName());
                    responseDTO.setAge(psychoIssueAnswer.getAge());
                    responseDTO.setTheraphyLanguage(psychoIssueAnswer.getTheraphyLanguage());
                    responseDTO.setPsychoGender(psychoIssueAnswer.getPsychoGender());
                    responseDTO.setCost(psychoIssueAnswer.getCost());
                    responseDTO.setTitle(psychoIssueAnswer.getTitle());
                    responseDTO.setUsername(psychoIssueAnswer.getUsername());
                    responseDTO.setSessionFor(psychoIssueAnswer.getSessionFor());

                    List<CheckClientSubTopicDTO> topics = new ArrayList<>();
                    if (psychoIssueAnswer.isValid()) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            topics = objectMapper.readValue(
                                    psychoIssueAnswer.getSubtopics(),
                                    new TypeReference<List<CheckClientSubTopicDTO>>() {
                                    }
                            );
                        } catch (JsonProcessingException e) {
                            return Mono.error(new RuntimeException("Failed to parse subtopics JSON", e));
                        }
                    }

                    responseDTO.setTopics(topics);
                    return Mono.just(responseDTO);
                })
                .switchIfEmpty(Mono.error(new TypedResponseException(
                        HttpStatus.NOT_FOUND,
                        "RESUME",
                        "Client resume not found"
                )));
    }
}