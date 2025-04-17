package org.monstis.group.qalbms.impl;

import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.*;
import org.monstis.group.qalbms.enums.Gender;
import org.monstis.group.qalbms.enums.PsychologicalIssue;
import org.monstis.group.qalbms.repository.ElasticSearchRepository;
import org.monstis.group.qalbms.service.PsychologistService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class PsychologsImpl implements PsychologistService {
    private final ElasticSearchRepository elasticSearchRepository;

    public PsychologsImpl(ElasticSearchRepository elasticSearchRepository) {
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @Override
    public Mono<Psychologist> addPsychologyist(PsychologistDTO psychologistDTO) {



        return elasticSearchRepository.findByPhoneNumber(psychologistDTO.getTherapist().getPhoneNumber())
                .switchIfEmpty(Mono.defer(() -> {
            Psychologist psychologist = new Psychologist();
            psychologist.setFirstName(psychologistDTO.getTherapist().getFirstName());
            psychologist.setGender(psychologistDTO.getTherapist().getGender().name());
            psychologist.setPriceForSession(psychologistDTO.getTherapist().getPriceForSession());
            psychologist.setFirstName(psychologistDTO.getTherapist().getFirstName());
            psychologist.setLastName(psychologistDTO.getTherapist().getLastName());
            psychologist.setDays(psychologistDTO.getTherapist().getDays());
            psychologist.setHours(psychologistDTO.getTherapist().getHours());
            psychologist.setPhoneNumber(psychologistDTO.getTherapist().getPhoneNumber());
            psychologist.setGender(psychologistDTO.getTherapist().getGender().name());
            psychologist.setPsychoIssues(List.of(PsychologicalIssue.ANXIETY.name(), PsychologicalIssue.DEPRESSION.name()));
            psychologist.setLicenseDocUrl(psychologistDTO.getLicense().getDocUrl());
            psychologist.setLicenseTitle(psychologistDTO.getLicense().getTitle());
            psychologist.setAdditionalInfoSubtitle(psychologistDTO.getAdditionalInfo().getSubtitle());
            psychologist.setAdditionalInfoTitle(psychologistDTO.getAdditionalInfo().getTitle());
            return elasticSearchRepository.save(psychologist);
        })).flatMap(psychologist1 -> {

                    psychologist1.setFirstName(psychologistDTO.getTherapist().getFirstName());
                    psychologist1.setGender(psychologistDTO.getTherapist().getGender().name());
                    psychologist1.setPriceForSession(psychologistDTO.getTherapist().getPriceForSession());
                    psychologist1.setFirstName(psychologistDTO.getTherapist().getFirstName());
                    psychologist1.setLastName(psychologistDTO.getTherapist().getLastName());
                    psychologist1.setPhoneNumber(psychologistDTO.getTherapist().getPhoneNumber());
                    psychologist1.setGender(psychologistDTO.getTherapist().getGender().name());
                    psychologist1.setDays(psychologistDTO.getTherapist().getDays());
                    psychologist1.setHours(psychologistDTO.getTherapist().getHours());
                    psychologist1.setPsychoIssues(List.of(PsychologicalIssue.ANXIETY.name(), PsychologicalIssue.DEPRESSION.name()));
                    psychologist1.setLicenseDocUrl(psychologistDTO.getLicense().getDocUrl());
                    psychologist1.setLicenseTitle(psychologistDTO.getLicense().getTitle());
                    psychologist1.setAdditionalInfoSubtitle(psychologistDTO.getAdditionalInfo().getSubtitle());
                    psychologist1.setAdditionalInfoTitle(psychologistDTO.getAdditionalInfo().getTitle());

            return elasticSearchRepository.save(psychologist1)
                    .onErrorResume(e -> {
                        log.error("Error saving psychologist: {}", e.getMessage());
                        return Mono.error(new Error("Error  occurred in saving"));
                    });
        });



    }


    @Override
    public Mono<ContentDTO> getAllPsychologyist() {
        return elasticSearchRepository.findAll()
                .map(psychologist -> {
                    SearchResultDTO psychologistDTO = new SearchResultDTO();

                    TherapistDTO therapistDTO = new TherapistDTO();
                    therapistDTO.setFirstName(psychologist.getFirstName());
                    therapistDTO.setLastName(psychologist.getLastName());
                    therapistDTO.setGender(Gender.valueOf(psychologist.getGender()));
                    therapistDTO.setPriceForSession(psychologist.getPriceForSession());
                    therapistDTO.setPhoneNumber(psychologist.getPhoneNumber());

                    AdditionalInfoDTO additionalInfoDTO = new AdditionalInfoDTO();
                    additionalInfoDTO.setSubtitle(psychologist.getAdditionalInfoSubtitle());
                    additionalInfoDTO.setTitle(psychologist.getAdditionalInfoTitle());

                    LicenseDTO licenseDTO = new LicenseDTO();
                    licenseDTO.setDocUrl(psychologist.getLicenseDocUrl());
                    licenseDTO.setTitle(psychologist.getLicenseTitle());

                    psychologistDTO.setTherapist(therapistDTO);
                    psychologistDTO.setAdditionalInfo(additionalInfoDTO);
                    psychologistDTO.setLicense(licenseDTO);

                    return psychologistDTO;
                })
                .collectList()
                .map(list -> {
                    ContentDTO contentDTO = new ContentDTO();
                    contentDTO.setContent(list); // may need casting if type mismatch
                    contentDTO.setCount(list.size());
                    contentDTO.setPrevKey(null); // or use pagination logic
                    contentDTO.setNextKey(null);
                    return contentDTO;
                });
    }
    @Override
    public Mono<CalendarContentDTO> getAllPsychologyistOnlyDate() {
        return elasticSearchRepository.findAll()
                .map(psychologist -> {
                    SessionCalendar psychologistDTO = new SessionCalendar();

                    TherapistDTO therapistDTO = new TherapistDTO();

                    therapistDTO.setDays(psychologist.getDays());
                    therapistDTO.setHours(psychologist.getHours());
                    AdditionalInfoDTO additionalInfoDTO = new AdditionalInfoDTO();
                    additionalInfoDTO.setSubtitle(psychologist.getAdditionalInfoSubtitle());
                    additionalInfoDTO.setTitle(psychologist.getAdditionalInfoTitle());



                    psychologistDTO.setDay(psychologist.getDays());
                    psychologistDTO.setHours(psychologist.getHours());



                    return psychologistDTO;
                })
                .collectList()
                .map(list -> {
                    CalendarContentDTO contentDTO = new CalendarContentDTO();
                    contentDTO.setContent(list); // may need casting if type mismatch
                    contentDTO.setPrevKey(null); // or use pagination logic
                    contentDTO.setNextKey(null);
                    contentDTO.setCount(String.valueOf(list.size()));
                    return contentDTO;
                });
    }

}

