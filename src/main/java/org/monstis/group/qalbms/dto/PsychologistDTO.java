package org.monstis.group.qalbms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import org.monstis.group.qalbms.enums.PsychologicalApproach;
import org.monstis.group.qalbms.enums.PsychologicalIssue;


import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
public class PsychologistDTO {


    private String firstName;

    private String lastName;

    private String age;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("gender")
    private String gender;


    @JsonProperty("video_url")
    private String videoUrl;

    @JsonProperty("psycho_calendar")
    private Map<String,String> psychoCalendar;


    @JsonProperty("price_for_session")
    private String priceForSession;


    private List<PsychologicalIssue> psychoIssues;

    private List<PsychologicalApproach>psychologicalApproaches;


}
