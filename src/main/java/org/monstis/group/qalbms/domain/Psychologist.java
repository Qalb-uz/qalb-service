package org.monstis.group.qalbms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Document(indexName = "therapist")
public class Psychologist  {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String sessionCost;

    @Field(type = FieldType.Keyword)
    private String imageUrl;

    private String additionalInfoTitle;
    private String additionalInfoSubtitle;
    private String about;
    private String licenseTitle;
    private String licenseDocUrl;
    private String methodTherapyTitle;
    private String methodTherapySubtitle;


    @Field(type = FieldType.Keyword)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private List<LocalDateTime> hours;

    @Field(type = FieldType.Keyword)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime days;

    @Field(type = FieldType.Keyword)
    private String phoneNumber;

    @Field(type = FieldType.Keyword)
    private String priceForSession;




    @Field(type = FieldType.Nested)
    private List<String> psychoIssues;


    @Field(type = FieldType.Keyword)
    private List<String>psychologicalApproaches;

}
