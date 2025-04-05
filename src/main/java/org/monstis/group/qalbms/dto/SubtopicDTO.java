package org.monstis.group.qalbms.dto;

public class SubtopicDTO {

    private String title;

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    private String additional;

    public SubtopicDTO( String title) {

        this.title = title;
    }

    // Getters and Setters

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
