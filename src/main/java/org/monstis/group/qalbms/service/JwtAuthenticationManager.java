package org.monstis.group.qalbms.service;

import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        if (jwtUtil.isValid(authToken)) {
            String username = jwtUtil.extractUsername(authToken);
            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, List.of()));
        }
        return Mono.empty();
    }
}