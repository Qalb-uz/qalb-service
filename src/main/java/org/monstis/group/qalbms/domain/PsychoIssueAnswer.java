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
    private Long id;

    private String category;
    private String subcategory;
    private String language;
    private String answer;
    private String phoneNumber;

    public PsychoIssueAnswer(PsychoIssueAnswerDTO dto) {
        this.category = dto.getCategory();
        this.subcategory = dto.getSubcategory();
        this.language = dto.getLanguage();
        this.answer = dto.getAnswer();
        this.phoneNumber = dto.getPhoneNumber();
    }
}
