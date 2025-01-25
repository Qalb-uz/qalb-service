package org.monstis.group.qalbms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.domain.Auth;
import org.monstis.group.qalbms.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
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
    private Mono<?>verifyOtp(@RequestParam("otp") String otp) {
        return authService.verifyOtp(otp).flatMap(Mono::just);
    }

    @PostMapping("/register")
    @Operation(summary = "registration", description = "REQUIRED_ROLES: <b></b>")
    private Mono<?>registerPhone(@RequestParam("phone") String phone) {
        return authService.registerPhone(phone);
    }

    @GetMapping("/terms-condition")
    @Operation(summary = "terms and condition file", description = "REQUIRED_ROLES: <b></b>")
    private Mono<String>termsAndCondition() {
        return Mono.just(termsAndConditions);
    }
}
