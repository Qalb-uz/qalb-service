package org.monstis.group.qalbms.service;

import lombok.extern.slf4j.Slf4j;
import org.monstis.group.qalbms.dto.EskizResponseDTO;
import org.monstis.group.qalbms.dto.EskizTokenDTO;
import org.monstis.group.qalbms.utils.OtpGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EskizWebClient {

    private final WebClient eskizWebClient;
    private static final Logger log = LoggerFactory.getLogger(EskizWebClient.class);

    public EskizWebClient(  WebClient eskizWebClient) {
        this.eskizWebClient = eskizWebClient;
    }

    public Mono<EskizTokenDTO> getToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("email", "rizoabdusattarov@icloud.com");
        formData.add("password", "PDCruGkMoqJ1FPwLG5Rib9CICQ7SFeTV0r9kyG13");

        log.info("Request to getToken from Eskiz");
               return eskizWebClient.post()
                .uri("/auth/login") // Replace with your endpoint
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(EskizTokenDTO.class);
    }

    public Mono<EskizResponseDTO> register(String phone, String token, int otp) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("mobile_phone", phone);
        formData.add("message", "Код для верификации в сервисе Qalb.uz: " + otp);
        formData.add("from", "4546");
        formData.add("callback_url","https://qalb.uz");

        log.info("Eskiz request to register phone({})", phone);
        log.info("Eskiz request to  get OtpCode({})", otp);

        return eskizWebClient.post()
                .uri("/message/sms/send")
                .header("Authorization","Bearer "+ token)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(EskizResponseDTO.class);
    }

}

