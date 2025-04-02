package org.monstis.group.qalbms.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class OtpResponse implements Serializable {
    private String message;
    private String phone;


    public OtpResponse(String otp_sent_successfully, String phone) {
        this.message=otp_sent_successfully;
        this.phone=phone;
    }
}


