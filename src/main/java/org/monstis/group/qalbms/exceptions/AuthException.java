package org.monstis.group.qalbms.exceptions;

import jakarta.validation.constraints.NotNull;

public class AuthException extends RuntimeException {

    public AuthException(@NotNull String message) {
        super(message);
    }
}
