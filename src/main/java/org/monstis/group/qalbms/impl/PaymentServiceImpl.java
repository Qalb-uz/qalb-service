package org.monstis.group.qalbms.impl;

import org.monstis.group.qalbms.domain.Card;
import org.monstis.group.qalbms.domain.Payment;
import org.monstis.group.qalbms.dto.*;
import org.monstis.group.qalbms.repository.CardRepository;
import org.monstis.group.qalbms.repository.PaymentRepository;
import org.monstis.group.qalbms.repository.SessionRepository;
import org.monstis.group.qalbms.service.PaymentService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class PaymentServiceImpl implements PaymentService {

    private final SessionRepository sessionRepository;
    private final CardRepository  cardRepository;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(SessionRepository sessionRepository, CardRepository cardRepository, PaymentRepository paymentRepository) {
        this.sessionRepository = sessionRepository;
        this.cardRepository = cardRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Mono<PaymentResponseDTO> createPayment(PaymentDTO paymentDTO, String username) {
        return sessionRepository.findByIdAndUsername(paymentDTO.getSessionId(),username).flatMap(sessionRes -> {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();

            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setSessionFor(sessionRes.getSession_for());
            sessionDTO.setId(String.valueOf(sessionRes.getId()));
            sessionDTO.setTime(String.valueOf(sessionRes.getTime()));
            sessionDTO.setPrice(sessionRes.getPrice());

          paymentResponseDTO.setSession(sessionDTO);


            SessionTherapistDTO sessionTherapistDTO = new SessionTherapistDTO();
            sessionTherapistDTO.setId(sessionRes.getTherapistId());
            sessionTherapistDTO.setFirstName(sessionRes.getTherapistName());
            sessionTherapistDTO.setLastName(sessionRes.getTherapistLastName());
            sessionTherapistDTO.setImg(sessionRes.getTherapistImage());

            paymentResponseDTO.setTherapist(sessionTherapistDTO);


            return cardRepository.findById(Long.valueOf(paymentDTO.getCardId())).flatMap(cardRes -> {
                Card card = new Card();
                card.setId(Long.valueOf(paymentDTO.getCardId()));
                card.setCardLogo(cardRes.getCardLogo());
                card.setCardPhoneNumber(cardRes.getCardPhoneNumber());
                card.setCardBrand(cardRes.getCardBrand());
                card.setPan(maskPan(cardRes.getPan()));
                card.setCardLogo(cardRes.getCardLogo());
                card.setCardPhoneNumber(cardRes.getCardPhoneNumber());
                paymentResponseDTO.setCard(card);
                Payment payment = new Payment();
                payment.setDate(String.valueOf(Instant.now()));
                payment.setCard_id(String.valueOf(cardRes.getId()));
                payment.setPrice(sessionRes.getPrice());
                payment.setSession_id(String.valueOf(sessionRes.getId()));
                payment.setTherapist_id(String.valueOf(sessionRes.getTherapistId()));


                return paymentRepository.save(payment).flatMap(payment1 -> {
                    paymentResponseDTO.setId(String.valueOf(payment1.getId()));
                    paymentResponseDTO.setDate(Instant.now().toString());
                    return Mono.just(paymentResponseDTO);
                });
            });
        });
    }
    public static String maskPan(String pan) {
        if (pan == null || pan.length() < 10) {
            return pan; // Not enough digits to mask properly
        }
        String firstFour = pan.substring(0, 4);
        String lastFour = pan.substring(pan.length() - 4);
        return firstFour + "******" + lastFour;
    }
}
