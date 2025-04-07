package org.monstis.group.qalbms.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class JwtUtil {
    public static String getUsername(String jwtToken) {
        try {
            String[] chunks = jwtToken.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(payload).get("preferred_username").asText();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT Token", e);
        }
    }
}
