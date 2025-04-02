package org.monstis.group.qalbms.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OtpGeneratorTest {


    @Test
    void otp_length_must_be_6() {
        final var generator = new OtpGenerator();
        final var otp = generator.generateOTP();

        Assertions.assertEquals(6, String.valueOf(otp).length());
    }
}