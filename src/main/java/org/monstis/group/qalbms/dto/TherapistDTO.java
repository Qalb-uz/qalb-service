package org.monstis.group.qalbms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.monstis.group.qalbms.enums.Gender;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public  class TherapistDTO{

    private String id;
    private String firstName;
    private String lastName;
    private String image;
    private Gender gender;
    private String priceForSession;
    private String phoneNumber;

    private LocalDateTime days;

    private List<LocalDateTime> hours;
}