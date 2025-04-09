//package org.monstis.group.qalbms.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import reactor.core.publisher.Mono;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class KeycloakService {
//
//    private final WebClient webClient;
//
//    public KeycloakService() {
//        this.webClient = WebClient.builder().build();
//    }
//
//    // Get Admin access token from Keycloak
//    public Mono<String> getAccessToken() {
//        return webClient.post()
//                .uri("https://23.239.18.240:8444/master/protocol/openid-connect/token")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .bodyValue("grant_type=password&client_id=admin-cli&username=admin&password=test")
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(response -> {
//                    ObjectMapper mapper = new ObjectMapper();
//                    try {
//                        return mapper.readTree(response).get("access_token").asText();
//                    } catch (Exception e) {
//                        throw new RuntimeException("Failed to parse access token", e);
//                    }
//                });
//    }
//
//    // Create a new user in Keycloak
//    public Mono<String> createUser(String token, String username) {
//        Map<String, Object> userPayload = new HashMap<>();
//        userPayload.put("username", username);
//        userPayload.put("enabled", true);
//        userPayload.put("email", username + "@example.com");
//
//        return webClient.post()
//                .uri("https://23.239.18.240:8444/admin/realms/master/users")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(userPayload)
//                .retrieve()
//                .toBodilessEntity()
//                .flatMap(response -> {
//                    String location = response.getHeaders().getFirst("Location");
//                    String userId = location != null ? location.substring(location.lastIndexOf('/') + 1) : null;
//                    return Mono.justOrEmpty(userId);
//                });
//    }
//
//    // Set the user's password in Keycloak
//    public Mono<Void> setUserPassword(String token, String userId, String password) {
//        Map<String, Object> passwordPayload = new HashMap<>();
//        passwordPayload.put("type", "password");
//        passwordPayload.put("temporary", false);
//        passwordPayload.put("value", password);
//
//        return webClient.put()
//                .uri("https://23.239.18.240:8444/admin/realms/master/users/" + userId + "/reset-password")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(passwordPayload)
//                .retrieve()
//                .toBodilessEntity()
//                .then();
//    }
//}
