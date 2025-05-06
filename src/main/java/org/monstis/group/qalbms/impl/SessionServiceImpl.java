package org.monstis.group.qalbms.impl;

import org.monstis.group.qalbms.domain.Session;
import org.monstis.group.qalbms.dto.*;
import org.monstis.group.qalbms.repository.ApplicationRepository;
import org.monstis.group.qalbms.repository.ElasticSearchRepository;
import org.monstis.group.qalbms.repository.SessionRepository;
import org.monstis.group.qalbms.service.CreateSessionService;
import org.monstis.group.qalbms.utils.TypedResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class SessionServiceImpl implements CreateSessionService {

   private final SessionRepository sessionRepository;
   private final ApplicationRepository applicationRepository;
   private final ElasticSearchRepository elasticSearchRepository;

    public SessionServiceImpl(SessionRepository sessionRepository, ApplicationRepository applicationRepository, ElasticSearchRepository elasticSearchRepository) {
        this.sessionRepository = sessionRepository;

        this.applicationRepository = applicationRepository;
        this.elasticSearchRepository = elasticSearchRepository;
    }


    @Override
    public Mono<SessionResponseDto> createSession(SessionRequestDTO session, String username, String deviceId) {
        LocalDateTime sessionTime = LocalDateTime.from(session.getSession_time());
        String therapistId = (session.getTherapist_id());

        return sessionRepository.findByTimeAndTherapistIdAndUsername(String.valueOf(sessionTime), (therapistId), username)
                .flatMap(existing -> Mono.<SessionResponseDto>error(
                        new TypedResponseException(HttpStatus.BAD_REQUEST, "SESSION", "This session already exists. Please choose a different time or therapist.")
                ))
                .switchIfEmpty(Mono.defer(() ->
                        applicationRepository.findFirstByUsername(username)
                                .flatMap(application -> {
                                    Session newSession = new Session();
                                    newSession.setTime(sessionTime);
                                    newSession.setTherapistId(session.getTherapist_id());
                                    newSession.setSession_for(application.getSessionFor());
                                    newSession.setUsername(username);
                                    newSession.setPrice(application.getCost());

                                    return elasticSearchRepository.findById(session.getTherapist_id())
                                            .flatMap(psychologist -> {
                                                newSession.setTherapistName(psychologist.getFirstName());
                                                newSession.setTherapistLastName(psychologist.getLastName());
                                                newSession.setTherapistImage(psychologist.getImageUrl());

                                                return sessionRepository.save(newSession)
                                                        .map(savedSession -> {

                                                            SessionDTO sessionDTO = new SessionDTO();
                                                            sessionDTO.setId(String.valueOf((savedSession.getId())));
                                                            sessionDTO.setTime(savedSession.getTime().toString());
                                                            sessionDTO.setSessionFor(savedSession.getSession_for());
                                                            sessionDTO.setPrice(savedSession.getPrice());

                                                            SessionTherapistDTO therapistDTO = new SessionTherapistDTO();
                                                            therapistDTO.setId((psychologist.getId()));
                                                            therapistDTO.setFirstName(psychologist.getFirstName());
                                                            therapistDTO.setLastName(psychologist.getLastName());
                                                            therapistDTO.setImg(psychologist.getImageUrl());

                                                            SessionResponseDto response = new SessionResponseDto();
                                                            response.setSession(sessionDTO);
                                                            response.setTherapist(therapistDTO);

                                                            return response;
                                                        });
                                            });
                                })
                ));
    }

    @Override
    public Mono<GetAllSessionListResponseDTO> getAllSession(String username, Integer key, Integer size) {
        int offset = key != null ? key : 0;

        return sessionRepository.findAllByUsername(username)
                .skip(offset)
                .take(size)
                .flatMap(session -> {
                    GetAllSessionResponseDTO response = new GetAllSessionResponseDTO();

                    SessionTopicDTO sessionDTO = new SessionTopicDTO();
                    sessionDTO.setId(String.valueOf(session.getId()));
                    sessionDTO.setTime(session.getTime().toString());
                    sessionDTO.setSessionFor(session.getSession_for());
                    sessionDTO.setPrice(session.getPrice());

                    SessionTherapistDTO therapistDTO = new SessionTherapistDTO();
                    therapistDTO.setId(session.getTherapistId());
                    therapistDTO.setFirstName(session.getTherapistName());
                    therapistDTO.setLastName(session.getTherapistLastName());
                    therapistDTO.setImg(session.getTherapistImage());

                    response.setSession(sessionDTO);
                    response.setTherapist(therapistDTO);

                    // Fetch related topics/subtopics
                    return applicationRepository.findAllByUsername(username)
                            .collectList()
                            .map(applications -> {
                                List<TopicDTO> topicDTOS = new ArrayList<>();

                                Map<Integer, TopicDTO> topicMap = new HashMap<>();
                                for (var answer : applications) {
                                    int topicId = answer.getId(); // assuming topicId is int
                                    topicMap.putIfAbsent(topicId, new TopicDTO(topicId, answer.getTitle(), new ArrayList<>()));
                                    topicMap.get(topicId).getSubtopics().add(
                                            new SubtopicDTO(answer.getId(), answer.getTitle())
                                    );
                                }

                                topicDTOS.addAll(topicMap.values());
                                sessionDTO.setTopic(topicDTOS);
                                return response;
                            });
                })
                .collectList()
                .flatMap(sessionList -> {
                    GetAllSessionListResponseDTO wrapper = new GetAllSessionListResponseDTO();
                    wrapper.setSessions(sessionList);
                    wrapper.setPrevKey(offset - size >= 0 ? offset - size : null);
                    wrapper.setNextKey(sessionList.size() == size ? offset + size : null);
                    wrapper.setCount(sessionList.size());
                    return Mono.just(wrapper);
                });
    }


}
