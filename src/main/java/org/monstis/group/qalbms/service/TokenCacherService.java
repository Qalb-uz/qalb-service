package org.monstis.group.qalbms.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class TokenCacherService {
    private static final Logger log = LoggerFactory.getLogger(TokenCacherService.class);
    private final Cache<String, String> tokenCache;

    public TokenCacherService(@Value("${eskiz.cache-token-lifetime}") Long tokenLifetime) {
        this.tokenCache = Caffeine.newBuilder().maximumSize(100L).expireAfterWrite(Duration.ofHours(tokenLifetime)).build();
    }

    public Optional<String> setToken(String username, String token) {
        log.trace("SET token to caffeine cache: {}", username);
        this.tokenCache.put(username, token);
        return Optional.of(token);
    }

    public Optional<String> getToken(String username) {
        log.trace("GET token from caffeine cache: {}", username);
        return Optional.ofNullable(this.tokenCache.getIfPresent(username));
    }
}
