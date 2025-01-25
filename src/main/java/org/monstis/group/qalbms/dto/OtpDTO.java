package org.monstis.group.qalbms.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtpDTO {

    private int id;
    private String otpCode;
    private String phoneNumber;
    private LocalDateTime localDateTime;

}
