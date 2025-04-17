package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.monstis.group.qalbms.enums.Gender;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public  class TherapistDTO{

    private String firstName;
    private String lastName;
    private String image;
    private Gender gender;
    private String priceForSession;
    private String phoneNumber;
    private String days;
    private List<String> hours;
}