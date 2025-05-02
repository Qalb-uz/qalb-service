package org.monstis.group.qalbms.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Data
@Getter
@Setter
public class EskizResponseDTO implements Serializable {

    private String id;
    private String message;
    private String status;
}
