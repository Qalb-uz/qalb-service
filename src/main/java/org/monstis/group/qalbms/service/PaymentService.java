package org.monstis.group.qalbms.service;

import org.monstis.group.qalbms.dto.PaymentDTO;
import org.monstis.group.qalbms.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface PaymentService {
    Mono<PaymentResponseDTO>createPayment(PaymentDTO paymentDTO,String username);
}
