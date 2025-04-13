package org.monstis.group.qalbms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import lombok.Data;
import org.monstis.group.qalbms.enums.PsychologicalApproach;
import org.monstis.group.qalbms.enums.PsychologicalIssue;


import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PsychologistDTO extends SearchResultDTO{

    private String id;

    private TherapistDTO therapist;
    private AdditionalInfoDTO additionalInfo;
    private LicenseDTO license;
}
