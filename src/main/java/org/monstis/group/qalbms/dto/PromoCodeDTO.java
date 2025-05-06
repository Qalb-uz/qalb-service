package org.monstis.group.qalbms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeDTO {

    private String promo_code;
    @JsonProperty("therapist_id")
    private String therapistId;
}
