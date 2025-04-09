package org.monstis.group.qalbms.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



public class TopicDTO {

    private String firstName;

    public TopicDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTheraphyLanguage() {
        return theraphyLanguage;
    }

    public void setTheraphyLanguage(String theraphyLanguage) {
        this.theraphyLanguage = theraphyLanguage;
    }

    public String getPsychoGender() {
        return psychoGender;
    }

    public void setPsychoGender(String psychoGender) {
        this.psychoGender = psychoGender;
    }

    public String getTherapyFor() {
        return therapyFor;
    }

    public void setTherapyFor(String therapyFor) {
        this.therapyFor = therapyFor;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(List<String> subtopics) {
        this.subtopics = subtopics;
    }

    private String age;
    private String theraphyLanguage;
    private String psychoGender;

    public TopicDTO(String firstName, String age, String theraphyLanguage, String psychoGender, String therapyFor, String cost, String title, List<String> subtopics) {
        this.firstName = firstName;
        this.age = age;
        this.theraphyLanguage = theraphyLanguage;
        this.psychoGender = psychoGender;
        this.therapyFor = therapyFor;
        this.cost = cost;
        this.title = title;
        this.subtopics = subtopics;
    }

    private String therapyFor;
    private String cost;

    private String title;
    private List<String> subtopics;



    public boolean isValid() {
        return  title != null && !title.trim().isEmpty() && subtopics != null && !subtopics.isEmpty()&& !cost.isEmpty()
                && !therapyFor.isEmpty()&& !firstName.isEmpty()&& !theraphyLanguage.isEmpty();
    }
}
