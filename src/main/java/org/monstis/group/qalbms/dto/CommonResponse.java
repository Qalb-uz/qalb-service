package org.monstis.group.qalbms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResponse implements Serializable {

    private Cause cause;
    private Boolean success;
}
