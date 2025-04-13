package org.monstis.group.qalbms.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TypedResponseException extends ResponseStatusException {
    private final String type;

    public TypedResponseException(HttpStatus status, String type, String reason) {
        super(status, reason);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
