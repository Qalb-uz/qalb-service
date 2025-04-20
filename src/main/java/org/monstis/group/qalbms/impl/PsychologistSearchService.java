package org.monstis.group.qalbms.impl;

import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.*;
import org.monstis.group.qalbms.enums.Gender;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class PsychologistSearchService {


    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    public PsychologistSearchService(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }


    public Mono<ContentDTO> searchPsychologists(String gender, String priceForSession, String psychoIssues, Integer size, String key) {
        Criteria criteria = new Criteria();

        if (gender != null) {
            criteria = criteria.and("gender").contains(gender);
        }

        if (priceForSession != null && !priceForSession.trim().isEmpty()) {
            criteria = criteria.and("priceForSession").contains(priceForSession.trim());
        }

        int page = key != null ? Integer.parseInt(key) : 0;

        Query query = new CriteriaQuery(criteria);
        query.setPageable(PageRequest.of(page, size));

        return reactiveElasticsearchTemplate.search(query, Psychologist.class)
                .map(hit -> {
                    Psychologist psychologist = hit.getContent();

                    TherapistDTO therapistDTO = new TherapistDTO();
                    therapistDTO.setFirstName(psychologist.getFirstName());
                    therapistDTO.setLastName(psychologist.getLastName());
                    therapistDTO.setGender(Gender.valueOf(psychologist.getGender()));
                    therapistDTO.setPriceForSession(psychologist.getPriceForSession());
                    therapistDTO.setPhoneNumber(psychologist.getPhoneNumber());
                    therapistDTO.setImage(psychologist.getImageUrl());

                    AdditionalInfoDTO additionalInfoDTO = new AdditionalInfoDTO();
                    additionalInfoDTO.setTitle(psychologist.getAdditionalInfoTitle());
                    additionalInfoDTO.setSubtitle(psychologist.getAdditionalInfoSubtitle());

                    LicenseDTO licenseDTO = new LicenseDTO();
                    licenseDTO.setTitle(psychologist.getLicenseTitle());
                    licenseDTO.setDocUrl(psychologist.getLicenseDocUrl());

                    SearchResultDTO result = new SearchResultDTO();
                    result.setTherapist(therapistDTO);
                    result.setAdditionalInfo(Collections.singletonList(additionalInfoDTO));
                    result.setLicense(Collections.singletonList(licenseDTO));

                    return result;
                })
                .collectList()
                .flatMap(results -> reactiveElasticsearchTemplate.count(query, Psychologist.class)
                        .map(count -> {
                            String prevKey = (page > 0) ? String.valueOf(page - 1) : null;
                            String nextKey = results.size() == size ? String.valueOf(page + 1) : null;

                            return new ContentDTO(results, count.intValue(), prevKey, nextKey);
                        }));
    }

//    public Mono<CalendarContentDTO> getCalendarDates(String gender, String priceForSession, String psychoIssues, Integer size, String key) {
//        Criteria criteria = new Criteria();
//
//        if (gender != null) {
//            criteria = criteria.and("gender").contains(gender);
//        }
//        if (gender != null) {
//            criteria = criteria.and("gender").contains(gender);
//        }
//
//        if (priceForSession != null && !priceForSession.trim().isEmpty()) {
//            criteria = criteria.and("priceForSession").contains(priceForSession.trim());
//        }
//
//        int page = key != null ? Integer.parseInt(key) : 0;
//
//        Query query = new CriteriaQuery(criteria);
//        query.setPageable(PageRequest.of(page, size));
//
//        return reactiveElasticsearchTemplate.search(query, Psychologist.class)
//                .map(hit -> {
//                    Psychologist psychologist = hit.getContent();
//                    CalendarContentDTO result = new CalendarContentDTO();
//                    result.setDay(psychologist.getDays());
//                    result.setHours((psychologist.getHours()));
//
//                    return result;
//                })
//                .collectList()
//                .flatMap(results -> reactiveElasticsearchTemplate.count(query, Psychologist.class)
//                        .map(count -> {
//                            String prevKey = (page > 0) ? String.valueOf(page - 1) : null;
//                            String nextKey = results.size() == size ? String.valueOf(page + 1) : null;
//
//                            return new CalendarContentDTO(results.get(0).getDay(),results.get(0).getHours());
//                        }));
//    }



}
