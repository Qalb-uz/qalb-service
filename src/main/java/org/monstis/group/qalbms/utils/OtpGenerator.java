package org.monstis.group.qalbms.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class OtpGenerator {

    public String generateOTP() {
        Random random = new Random();
        Integer otpCode = 100000 + random.nextInt(900000);
        log.info("OTP generated : {}", otpCode);
        return String.valueOf(otpCode);
    }
}
