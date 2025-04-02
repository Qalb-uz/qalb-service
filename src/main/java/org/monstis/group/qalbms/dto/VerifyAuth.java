package org.monstis.group.qalbms.dto;

import jakarta.validation.constraints.NotNull;

public interface VerifyAuth {

    record Response(
            @NotNull String accessToken,
            @NotNull String refreshToken
    ) {
    }

    record Request(
            @NotNull String msisdn,
            @NotNull String otp
    ) {
    }
}
