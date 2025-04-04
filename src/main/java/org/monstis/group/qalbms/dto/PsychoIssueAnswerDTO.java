package org.monstis.group.qalbms.dto;

import lombok.Data;

@Data
public class PsychoIssueAnswerDTO {
    private String category;
    private String subcategory;
    private String language;
    private String answer;
    private String phoneNumber;
}
