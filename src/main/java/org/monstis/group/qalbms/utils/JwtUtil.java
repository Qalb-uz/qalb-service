package org.monstis.group.qalbms.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.monstis.group.qalbms.dto.TokenResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey = "aGVsbG9fcXVhbGItZGV2X3NlY3JldF9sb25nX3NlY3JldA==";

    public TokenResponse generateTokens(String username, String deviceId, String deviceName) {
        String accessToken = Jwts.builder()
                .setSubject(username)
                .claim("deviceId", deviceId)
                .claim("deviceName", deviceName)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .claim("deviceId", deviceId)
                .claim("deviceName", deviceName)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS))) // longer expiry
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(String refreshToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String username = claims.getSubject();
            String deviceId = claims.get("deviceId", String.class);
            String deviceName = claims.get("deviceName", String.class);

            return generateTokens(username, deviceId, deviceName);

        } catch (ExpiredJwtException e) {
            // Optionally, you can still extract claims from expired token
            Claims claims = e.getClaims();
            String username = claims.getSubject();
            String deviceId = claims.get("deviceId", String.class);
            String deviceName = claims.get("deviceName", String.class);

            return generateTokens(username, deviceId, deviceName);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    public String extractDeviceId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("deviceId", String.class);
    }
    public String extractDeviceName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("deviceName", String.class);
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValid(String token) {
        try {
            extractUsername(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}