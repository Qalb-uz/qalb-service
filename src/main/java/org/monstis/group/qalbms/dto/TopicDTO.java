package org.monstis.group.qalbms.dto;

import lombok.*;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private int id;
    private String title;
    private List<SubtopicDTO> subtopics;
}
