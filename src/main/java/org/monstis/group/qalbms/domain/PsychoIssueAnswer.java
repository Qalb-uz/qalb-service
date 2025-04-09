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

    private String topicTitle;
    private String username;
    private String subtopicTitle;
    private String additional;

    public PsychoIssueAnswer( String topicTitle, String additional, String subtopicTitle,String username) {
        this.topicTitle = topicTitle;
        this.additional=additional;
        this.subtopicTitle = subtopicTitle;
        this.username = username;
    }

}
