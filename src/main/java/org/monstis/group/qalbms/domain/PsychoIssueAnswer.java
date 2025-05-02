package org.monstis.group.qalbms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
