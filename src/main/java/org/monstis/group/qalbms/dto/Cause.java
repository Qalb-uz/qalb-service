package org.monstis.group.qalbms.dto;

import lombok.Data;

@Data
public class Cause {

    private String type;

    public Cause(String type, String message) {
        this.type = type;
        this.message = message;
    }

    private String message;

}
