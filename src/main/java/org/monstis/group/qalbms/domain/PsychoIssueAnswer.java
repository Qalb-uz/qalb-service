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

    private String subtopicTitle;
    private String additional;

    public PsychoIssueAnswer(int topicId, String topicTitle, String additional, String subtopicTitle) {
        this.topicId = topicId;
        this.topicTitle = topicTitle;
        this.additional=additional;
        this.subtopicTitle = subtopicTitle;
    }

    // Getters/Setters
}
