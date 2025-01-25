package org.monstis.group.qalbms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component

public class OtpGenerator {
    private static final Logger log = LoggerFactory.getLogger(OtpGenerator.class);

    public String generateOTP() {
        Random random = new Random();
        Integer otpCode = 100000 + random.nextInt(900000);
        log.info("OTP generated : {}", otpCode);
        return String.valueOf(otpCode);
    }
}
