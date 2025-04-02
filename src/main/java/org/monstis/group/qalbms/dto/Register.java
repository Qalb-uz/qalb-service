package org.monstis.group.qalbms.dto;

import jakarta.validation.constraints.NotNull;

public interface Register {

    record Response(@NotNull String msisdn, @NotNull Long retry) {
    }

    class Request {

    }
}
