package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDTO {
    
    private TherapistDTO therapist;
    private AdditionalInfoDTO additionalInfo;
    private LicenseDTO license;
}
