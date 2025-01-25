package org.monstis.group.qalbms.domain;

import lombok.Getter;
import lombok.Setter;
import org.monstis.group.qalbms.enums.PsychologicalIssue;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.security.DrbgParameters;
import java.util.List;

@Getter
@Setter
@Document(indexName = "psychologist", shards = 1, replicas = 0, refreshInterval = "-1")
public class Psychologist {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String age;
    private String videoUrl;
    private String phoneNumber;
    private String sex;
    private String cost;
    private List<PsychologicalIssue> functionality;

}