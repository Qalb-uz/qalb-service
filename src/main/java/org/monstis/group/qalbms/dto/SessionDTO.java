package org.monstis.group.qalbms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private String id;
    private String sessionFor;
    private String price;
    private String time;
    private TemporalAccessor payment_due_time= (Instant.now().plusSeconds(20l));

    @JsonIgnore
    private String username;


}