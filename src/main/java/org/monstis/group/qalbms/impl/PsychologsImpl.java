package org.monstis.group.qalbms.impl;

import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Psychologist;
import org.monstis.group.qalbms.dto.*;
import org.monstis.group.qalbms.enums.Gender;
import org.monstis.group.qalbms.enums.PsychologicalIssue;
import org.monstis.group.qalbms.repository.ElasticSearchRepository;
import org.monstis.group.qalbms.service.PsychologistService;
import org.monstis.group.qalbms.utils.TypedResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

            MethodTherapyDTO methodTherapyDTO = new MethodTherapyDTO();
            methodTherapyDTO.setSubtitle(psychologist.getMethodTherapySubtitle());
            methodTherapyDTO.setTitle(psychologist.getMethodTherapyTitle());

            psychologist.setMethodTherapySubtitle(methodTherapyDTO.getSubtitle());
            psychologist.setMethodTherapyTitle(methodTherapyDTO.getTitle());
            psychologist.setFirstName(psychologistDTO.getTherapist().getFirstName());
            psychologist.setGender(psychologistDTO.getTherapist().getGender().name());
            psychologist.setPriceForSession(psychologistDTO.getTherapist().getPriceForSession());
            psychologist.setFirstName(psychologistDTO.getTherapist().getFirstName());
            psychologist.setLastName(psychologistDTO.getTherapist().getLastName());
            psychologist.setDays((psychologistDTO.getTherapist().getDays()));
            psychologist.setHours(psychologistDTO.getTherapist().getHours());
            psychologist.setPhoneNumber(psychologistDTO.getTherapist().getPhoneNumber());
            psychologist.setGender(psychologistDTO.getTherapist().getGender().name());
            psychologist.setPsychoIssues(List.of(PsychologicalIssue.ANXIETY.name(), PsychologicalIssue.DEPRESSION.name()));

            List<AdditionalInfoDTO>addInfoList=psychologistDTO.getAdditionalInfo();
            addInfoList.forEach(additionalInfoDTO -> {
                psychologist.setAdditionalInfoSubtitle(additionalInfoDTO.getSubtitle());
                psychologist.setAdditionalInfoTitle(additionalInfoDTO.getTitle());
            });
            List<LicenseDTO>licenseDTOList=psychologistDTO.getLicense();
            licenseDTOList.forEach(licenseDTORes -> {
                psychologist.setLicenseDocUrl(licenseDTORes.getDocUrl());
                psychologist.setLicenseTitle(licenseDTORes.getTitle());

            });




            return elasticSearchRepository.save(psychologist);
        })).flatMap(psychologist1 -> {

                    psychologist1.setFirstName(psychologistDTO.getTherapist().getFirstName());
                    psychologist1.setGender(psychologistDTO.getTherapist().getGender().name());
                    psychologist1.setPriceForSession(psychologistDTO.getTherapist().getPriceForSession());
                    psychologist1.setFirstName(psychologistDTO.getTherapist().getFirstName());
                    psychologist1.setLastName(psychologistDTO.getTherapist().getLastName());
                    psychologist1.setPhoneNumber(psychologistDTO.getTherapist().getPhoneNumber());
                    psychologist1.setGender(psychologistDTO.getTherapist().getGender().name());
                    psychologist1.setDays((psychologistDTO.getTherapist().getDays()));
                    psychologist1.setHours(psychologistDTO.getTherapist().getHours());
                    psychologist1.setPsychoIssues(List.of(PsychologicalIssue.ANXIETY.name(), PsychologicalIssue.DEPRESSION.name()));

                    MethodTherapyDTO methodTherapyDTO = new MethodTherapyDTO();
                    methodTherapyDTO.setSubtitle(psychologist1.getMethodTherapySubtitle());
                    methodTherapyDTO.setTitle(psychologist1.getMethodTherapyTitle());

                    psychologist1.setMethodTherapySubtitle(methodTherapyDTO.getSubtitle());
                    psychologist1.setMethodTherapyTitle(methodTherapyDTO.getTitle());

                    List<AdditionalInfoDTO>addInfoList=psychologistDTO.getAdditionalInfo();
                    addInfoList.forEach(additionalInfoDTO -> {
                        psychologist1.setAdditionalInfoSubtitle(additionalInfoDTO.getSubtitle());
                        psychologist1.setAdditionalInfoTitle(additionalInfoDTO.getTitle());
                    });
                    List<LicenseDTO>licenseDTOList=psychologistDTO.getLicense();
                    licenseDTOList.forEach(licenseDTORes -> {
                        psychologist1.setLicenseDocUrl(licenseDTORes.getDocUrl());
                        psychologist1.setLicenseTitle(licenseDTORes.getTitle());

                    });
            return elasticSearchRepository.save(psychologist1)
                    .onErrorResume(e -> {
                        log.error("Error saving psychologist: {}", e.getMessage());
                        return Mono.error(new Error("Error  occurred in saving"));
                    });
        });



    }


    public Mono<ContentDTO> getAllPsychologyist(Integer page, Integer size) {
        int skip = page * size;

        return elasticSearchRepository.findAll()
                .skip(skip)
                .take(size)
                .map(psychologist -> {
                    SearchResultDTO psychologistDTO = new SearchResultDTO();
                    TherapistDTO therapistDTO = new TherapistDTO();
                    therapistDTO.setId(psychologist.getId());
                    therapistDTO.setFirstName(psychologist.getFirstName());
                    therapistDTO.setLastName(psychologist.getLastName());
                    therapistDTO.setGender(Gender.valueOf(psychologist.getGender()));
                    therapistDTO.setPriceForSession(psychologist.getPriceForSession());
                    therapistDTO.setPhoneNumber(psychologist.getPhoneNumber());
                    psychologistDTO.setAbout(psychologist.getAbout());

                    therapistDTO.setHours(psychologist.getHours());
                    therapistDTO.setDays(psychologist.getDays());
                    therapistDTO.setImage(psychologist.getImageUrl());

                    AdditionalInfoDTO additionalInfoDTO = new AdditionalInfoDTO();
                    additionalInfoDTO.setSubtitle(psychologist.getAdditionalInfoSubtitle());
                    additionalInfoDTO.setTitle(psychologist.getAdditionalInfoTitle());

                    LicenseDTO licenseDTO = new LicenseDTO();
                    licenseDTO.setDocUrl(psychologist.getLicenseDocUrl());
                    licenseDTO.setTitle(psychologist.getLicenseTitle());

                    MethodTherapyDTO methodTherapyDTO = new MethodTherapyDTO();
                    methodTherapyDTO.setSubtitle(psychologist.getMethodTherapySubtitle());
                    methodTherapyDTO.setTitle(psychologist.getMethodTherapyTitle());

                    psychologistDTO.setTherapist(therapistDTO);
                    psychologistDTO.setAdditionalInfo(Collections.singletonList(additionalInfoDTO));
                    psychologistDTO.setMethodTherapy(Collections.singletonList(methodTherapyDTO));
                    psychologistDTO.setLicense(Collections.singletonList(licenseDTO));

                    return psychologistDTO;
                })
                .collectList()
                .map(list -> {
                    ContentDTO contentDTO = new ContentDTO();
                    contentDTO.setContent(list);
                    contentDTO.setCount(list.size());
                    contentDTO.setPrevKey(String.valueOf(page > 0 ? page - 1 : null));
                    contentDTO.setNextKey(String.valueOf(list.size() == size ? page + 1 : null));
                    return contentDTO;
                });
    }
    @Override
    public Mono<List<CalendarContentDTO>> findByPsychologId(String psychologistId) {
        return elasticSearchRepository.findById(psychologistId)
                .map(psychologist -> {
                    List<LocalDateTime> hours = psychologist.getHours(); // assume this returns List<LocalDateTime>

                    // Group by date (day-only), using midnight to represent the day key
                    Map<LocalDateTime, List<LocalDateTime>> grouped = hours.stream()
                            .collect(Collectors.groupingBy(hour -> hour.toLocalDate().atStartOfDay()));

                    // Convert each group into CalendarContentDTO
                    return grouped.entrySet().stream()
                            .map(entry -> {
                                CalendarContentDTO dto = new CalendarContentDTO();
                                dto.setDay(entry.getKey()); // the "day"
                                dto.setHours(entry.getValue()); // the "hours"
                                return dto;
                            })
                            .collect(Collectors.toList());
                })
                .switchIfEmpty(Mono.error(new TypedResponseException(
                        HttpStatus.NOT_FOUND, "Psychology", "Psychology id not found")));
    }



}

