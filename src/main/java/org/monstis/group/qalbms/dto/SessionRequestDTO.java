package org.monstis.group.qalbms.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionRequestDTO {

    @JsonProperty("session_time")
    private OffsetDateTime session_time;
    @JsonProperty("therapist_id")
    private String therapist_id;


}


