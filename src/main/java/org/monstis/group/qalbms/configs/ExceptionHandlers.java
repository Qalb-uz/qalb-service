package org.monstis.group.qalbms.configs;


import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.dto.Cause;
import org.monstis.group.qalbms.dto.CommonResponse;
import org.monstis.group.qalbms.exceptions.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

    private static final String UNKNOWN_EXCEPTION_TYPE = "unknown";
    private static final String AUTH_EXCEPTION_TYPE = "auth";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse serverExceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new CommonResponse(new Cause(UNKNOWN_EXCEPTION_TYPE, ex.getMessage()), false);
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handle(AuthException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonResponse(new Cause(AUTH_EXCEPTION_TYPE, ex.getMessage()), false);
    }
}