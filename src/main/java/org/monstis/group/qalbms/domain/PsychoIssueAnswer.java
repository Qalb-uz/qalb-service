package org.monstis.group.qalbms.domain;

import lombok.*;
import org.monstis.group.qalbms.dto.PsychoIssueAnswerDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("client_application")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PsychoIssueAnswer {
    @Id
    private String id;

    @Column("first_name")
    private String firstName;
    private String age;

    @Column("theraphy_language")
    private String theraphyLanguage;

    @Column("psycho_gender")
    private String psychoGender;
    @Column("therapy_for")
    private String therapyFor;

    private String username;

    private String cost;
    private String title;
    @Column("sub_topics")
    private List<String> subtopics;


    public PsychoIssueAnswer(String title, String firstName, String age, String theraphyLanguage, String psychoGender, List<String> subtopics, String username) {
        this.id = id;
        this.firstName = firstName;
        this.age = age;
        this.theraphyLanguage = theraphyLanguage;
        this.psychoGender = psychoGender;
        this.therapyFor = therapyFor;
        this.cost = cost;
        this.title = title;
        this.subtopics = subtopics;
        this.username = username;
    }
}
