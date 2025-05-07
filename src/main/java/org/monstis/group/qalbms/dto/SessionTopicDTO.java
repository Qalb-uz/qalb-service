package org.monstis.group.qalbms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.temporal.TemporalAccessor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionTopicDTO {
    private String id;
    private String sessionFor;
    private String price;
    private String time;
    private List<TopicDTO> topic;
    private TemporalAccessor payment_due_time = (java.time.Instant.now().plusSeconds(20L));

    @JsonIgnore
    private String username;


}