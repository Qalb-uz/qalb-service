package org.monstis.group.qalbms.domain;

import lombok.*;
import org.monstis.group.qalbms.enums.PsychologicalApproach;
import org.monstis.group.qalbms.enums.PsychologicalIssue;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Map;


@Data
@Document(indexName = "psychologist")
public class Psychologist  {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String age;

    @Field(type = FieldType.Keyword)
    private String videoUrl;


    @Field(type = FieldType.Keyword)
    private Map<String,String> psychoCalendar;

    @Field(type = FieldType.Keyword)
    private String phoneNumber;

    @Field(type = FieldType.Keyword)
    private String priceForSession;

    @Field(type = FieldType.Keyword)
    private String gender;

    private List<PsychologicalIssue> psychoIssues;
    private List<PsychologicalApproach>psychologicalApproaches;

}