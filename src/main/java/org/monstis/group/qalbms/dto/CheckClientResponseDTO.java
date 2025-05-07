package org.monstis.group.qalbms.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckClientResponseDTO {


    private Integer id;

    @JsonProperty("first_name")
    private String firstName;

    private String age;

    @JsonProperty("theraphy_language")
    private String theraphyLanguage;

    @JsonProperty("psycho_gender")
    private String psychoGender;

    @JsonProperty("session_for")
    private String sessionFor;

    private String username;
    private List<CheckClientSubTopicDTO> subtopic;
    private String cost;
    private String title;


}
