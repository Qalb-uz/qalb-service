package org.monstis.group.qalbms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResponse implements Serializable {

    private Cause cause;

    public CommonResponse(Cause cause, Boolean success) {
        this.cause = cause;
        this.success = success;
    }

    private Boolean success;
}
