package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionTherapistDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String image;
}
