package org.monstis.group.qalbms.dto;

import java.util.List;

public class TopicDTO {
    private int id;
    private String title;
    private List<SubtopicDTO> subtopics;

    public TopicDTO(int id, String title, List<SubtopicDTO> subtopics) {
        this.id = id;
        this.title = title;
        this.subtopics = subtopics;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<SubtopicDTO> getSubtopics() { return subtopics; }
    public void setSubtopics(List<SubtopicDTO> subtopics) { this.subtopics = subtopics; }
}
