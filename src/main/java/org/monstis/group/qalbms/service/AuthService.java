package org.monstis.group.qalbms.service;



import org.monstis.group.qalbms.dto.OtpResponse;
import org.monstis.group.qalbms.dto.VerifyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public interface AuthService {

    Mono<VerifyDTO>verifyOtp(String otp, String phone,String devicName,String deviceId);

    Mono<ResponseEntity<OtpResponse>>registerPhone(String phone,String deviceName,String deviceId);

    Mono<Optional<String>>setTokenToLocalCache();

    Mono<Optional<String>>getTokenFromLocalCache();
}
