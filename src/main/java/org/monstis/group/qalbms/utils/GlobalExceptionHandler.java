package org.monstis.group.qalbms.utils;

import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.dto.Cause;
import org.monstis.group.qalbms.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice

public class GlobalExceptionHandler {

    private static final String UNKNOWN_EXCEPTION_TYPE = "unknown";
    private static final String AUTH_EXCEPTION_TYPE = "auth";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse serverExceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new CommonResponse(new Cause(UNKNOWN_EXCEPTION_TYPE, ex.getMessage()), false);
    }


    @ExceptionHandler(TypedResponseException.class)
    public Mono<CommonResponse> handleTypedException(TypedResponseException ex) {
        return Mono.just(new CommonResponse(
                new Cause(ex.getType(), ex.getReason()),
                false
        ));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<CommonResponse> handleDefaultException(ResponseStatusException ex) {
        return Mono.just(new CommonResponse(
                new Cause("UNKNOWN", ex.getReason()),
                false
        ));
    }
}
