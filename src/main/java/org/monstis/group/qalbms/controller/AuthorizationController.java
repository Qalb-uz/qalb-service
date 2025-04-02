package org.monstis.group.qalbms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.dto.VerifyAuth;
import org.monstis.group.qalbms.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Tag(name = "Qalb project REST APIs", description = "REST APIs")
public class AuthorizationController {

    private final AuthService authService;

    @Value("${termsFile}")
    private String termsAndConditions;

    @PostMapping("/verify-otp")
    @Operation(summary = "verify otp code", description = "REQUIRED_ROLES: <b></b>")
    private Mono<?> verifyOtp(@RequestBody VerifyAuth.Request request) {
        return authService.verifyOtp(request.msisdn(), request.otp()).flatMap(Mono::just);
    }

    @PostMapping("/register")
    @Operation(summary = "registration", description = "REQUIRED_ROLES: <b></b>")
    private Mono<?> registerPhone(@RequestParam("phone") String phone) {
        return authService.registerPhone(phone);
    }

    @GetMapping("/terms-condition")
    @Operation(summary = "terms and condition file", description = "REQUIRED_ROLES: <b></b>")
    private Mono<String> termsAndCondition() {
        return Mono.just(termsAndConditions);
    }
}
