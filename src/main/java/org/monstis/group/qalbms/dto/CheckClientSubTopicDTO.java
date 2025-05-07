package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckClientSubTopicDTO {
    private int id;
    private String title;
    private List<SubtopicDTO> subtopics;

}
