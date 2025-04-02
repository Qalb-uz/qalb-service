package org.monstis.group.qalbms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Auth;
import org.monstis.group.qalbms.dto.OtpResponse;
import org.monstis.group.qalbms.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "REST APIs")
public class AuthorizationController {
    private final AuthService authService;

    @Value("${termsFile}")
    private String termsAndConditions;

    public AuthorizationController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/verify-otp")
    @Operation(summary = "verify otp code", description = "REQUIRED_ROLES: <b></b>")
    private Mono<?>verifyOtp(@RequestParam("otp") String otp,@RequestParam("otp") String phone) {
        return authService.verifyOtp(otp,phone).flatMap(Mono::just);
    }

    @Operation(summary = "Register phone and send OTP",
            responses = @ApiResponse(responseCode = "200", description = "OTP sent",
                    content = @Content(schema = @Schema(implementation = OtpResponse.class))))
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<OtpResponse>> registerPhone(@RequestParam String phone) {
        return authService.registerPhone(phone);
    }


    @GetMapping("/terms-condition")
    @Operation(summary = "terms and condition file", description = "REQUIRED_ROLES: <b></b>")
    private Mono<String>termsAndCondition() {
        return Mono.just(termsAndConditions);
    }
}
