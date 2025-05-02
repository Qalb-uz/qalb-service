package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PsychologistDTO extends SearchResultDTO{

    private TherapistDTO therapist;
    private List<AdditionalInfoDTO> additionalInfo;
    private List<LicenseDTO> license;
    private List<MethodTherapyDTO> methodTherapy;
}
