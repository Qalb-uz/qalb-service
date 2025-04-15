package org.monstis.group.qalbms.domain;

import jakarta.json.Json;
import lombok.*;
import org.monstis.group.qalbms.dto.PsychoIssueAnswerDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nullable;
import java.util.List;

@Table("client_application")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PsychoIssueAnswer {
    @Id
    private Integer id;

    @Column("first_name")
    private String firstName;
    private String age;

    @Column("theraphy_language")
    private String theraphyLanguage;

    @Column("psycho_gender")
    private String psychoGender;
    @Column("session_for")
    private String sessionFor;

    private String username;

    private String cost;
    private String title;
    @Column("sub_topics")
    private String subtopics;



public boolean isValid() {
    return title != null && !title.trim().isEmpty()
        && subtopics != null && !subtopics.trim().isEmpty()
        && cost != null && !cost.trim().isEmpty()
        && sessionFor != null && !sessionFor.trim().isEmpty()
        && firstName != null && !firstName.trim().isEmpty()
        && theraphyLanguage != null && !theraphyLanguage.trim().isEmpty();
}
}
