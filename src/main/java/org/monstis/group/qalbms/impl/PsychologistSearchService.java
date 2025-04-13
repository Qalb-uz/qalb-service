package org.monstis.group.qalbms.impl;

import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.*;
import org.monstis.group.qalbms.enums.Gender;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PsychologistSearchService {


    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    public PsychologistSearchService(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }

    public Flux<ContentDTO> searchPsychologists(String gender, String priceForSession, String psychoIssues, Integer size, String key) {

        Criteria criteria = new Criteria();

        if (gender != null) {
            criteria = criteria.and("gender").contains(gender);
        }

//        if (psychoIssues != null) {
//            criteria = criteria.and("psychoIssues").is(psychoIssues);
//        }
        if (priceForSession != null && !priceForSession.trim().isEmpty()) {
            criteria = criteria.and("priceForSession").contains(priceForSession.trim());
        }

        Query query = new CriteriaQuery(criteria);

        PageRequest pageRequest = PageRequest.of(0, size);
        query.setPageable(pageRequest);

        return reactiveElasticsearchTemplate.search(query, Psychologist.class)
                .flatMap(psychologistSearchHit -> {
                    Psychologist psychologist = psychologistSearchHit.getContent();

                    // Create DTO for Psychologist
                    ContentDTO psychologistDTO = new ContentDTO();
                    TherapistDTO therapistDTO = new TherapistDTO();
                    therapistDTO.setFirstName(psychologist.getFirstName());
                    therapistDTO.setLastName(psychologist.getLastName());
                    therapistDTO.setGender(Gender.valueOf(psychologist.getGender()));
                    therapistDTO.setPriceForSession(psychologist.getPriceForSession());
                    therapistDTO.setPhoneNumber(psychologist.getPhoneNumber());
                    psychologistDTO.setCount(5);


                    // Additional Info mapping
                    AdditionalInfoDTO additionalInfoDTO = new AdditionalInfoDTO();
                    additionalInfoDTO.setSubtitle(psychologist.getAdditionalInfoSubtitle());
                    additionalInfoDTO.setTitle(psychologist.getAdditionalInfoTitle());

                    // License mapping
                    LicenseDTO licenseDTO = new LicenseDTO();
                    licenseDTO.setDocUrl(psychologist.getLicenseDocUrl());
                    licenseDTO.setTitle(psychologist.getLicenseTitle());

                    // Set all DTO fields
                    psychologistDTO.setContent(psychologistDTO.getContent());
//                    psychologistDTO.setAdditionalInfo(additionalInfoDTO);
//                    psychologistDTO.setLicense(licenseDTO);


                    return reactiveElasticsearchTemplate.count(query, Psychologist.class).flatMap(aLong -> {
                        psychologistDTO.setCount(Math.toIntExact(aLong));
                        psychologistDTO.setNextKey(psychologistSearchHit.getId());
                        psychologistDTO.setPrevKey(String.valueOf(aLong-1));
                        return Mono.just(psychologistDTO);
                    });
                });
    }


}
