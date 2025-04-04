package org.monstis.group.qalbms.domain;

import lombok.*;
import org.monstis.group.qalbms.dto.PsychoIssueAnswerDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("psycho_issue_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PsychoIssueAnswer {
    @Id
    private String id;

    private int topicId;
    private String topicTitle;

    private int subtopicId;
    private String subtopicTitle;

    public PsychoIssueAnswer(int topicId, String topicTitle, int subtopicId, String subtopicTitle) {
        this.topicId = topicId;
        this.topicTitle = topicTitle;
        this.subtopicId = subtopicId;
        this.subtopicTitle = subtopicTitle;
    }

    // Getters/Setters
}
