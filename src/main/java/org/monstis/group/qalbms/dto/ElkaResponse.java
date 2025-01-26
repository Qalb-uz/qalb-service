package org.monstis.group.qalbms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ElkaResponse implements Serializable {

    private String total;
    private String successful;
    private String failed;

}
