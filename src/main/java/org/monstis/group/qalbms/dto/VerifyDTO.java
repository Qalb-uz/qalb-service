package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyDTO {
    private DeviceDto device;
    private UserDto user;
    private String resumeStatus;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceDto {
        private String id;
        private String name;
        private String refreshToken;
        private String accessToken;
        private Instant firstLoginAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        private Long id;
        private String name;
        private String msisdn;
    }

    public enum ResumeStatus {
        VALID, INVALID
    }
}
