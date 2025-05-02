package org.monstis.group.qalbms.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.monstis.group.qalbms.domain.Card;
import org.monstis.group.qalbms.dto.CardDTO;
import org.monstis.group.qalbms.dto.PageTwoDTO;
import org.monstis.group.qalbms.dto.PaymentDTO;
import org.monstis.group.qalbms.repository.CardRepository;
import org.monstis.group.qalbms.service.AuthService;
import org.monstis.group.qalbms.service.CardService;
import org.monstis.group.qalbms.service.PromoService;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
@Slf4j
@Tag(name = "Qalb project REST APIs", description = "REST APIs")
public class PaymentsController {

    private final CardService cardService;
    private final JwtUtil jwtUtil;
    private  final AuthService authService;
    private final CardRepository cardRepository;
    private final PromoService promoService;

    public PaymentsController(CardService cardService, JwtUtil jwtUtil, AuthService authService, CardRepository cardRepository, PromoService promoService) {
        this.cardService = cardService;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.cardRepository = cardRepository;
        this.promoService = promoService;
    }

    @PostMapping("add/card")
    public Mono<?>addCard(@RequestBody CardDTO card, ServerWebExchange exchange){
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        String username=jwtUtil.extractUsername(token);
        return cardService.addCard(card,username,token)
                .doOnNext(c -> log.info("Card added successfully: {}", c))
                .doOnError(e -> log.error("Error adding card: {}", e.getMessage()));
    }
    @PostMapping("/verify-card-otp")
    @Operation(summary = "verify otp code", description = "REQUIRED_ROLES: <b></b>")
    public Mono<Card> verifyOtp(@RequestParam("otp") String otp,ServerWebExchange serverWebExchange) {
        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);

        return authService.verifyOtp(otp, jwtUtil.extractUsername(token), jwtUtil.extractDeviceName(token), jwtUtil.extractDeviceId(token))
                .flatMap(verifyDTO -> cardRepository.findByCardPhoneNumber(jwtUtil.extractUsername(token)));
    }
    @GetMapping("/get-cards")
    @Operation(summary = "verify otp code", description = "REQUIRED_ROLES: <b></b>")
    public Mono<Card>getClientAllCards(ServerWebExchange serverWebExchange){
        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        return cardRepository.findByCardPhoneNumber(jwtUtil.extractUsername(token));
    }

  @PostMapping("check-promo")
  @Operation(summary = "verify otp code", description = "REQUIRED_ROLES: <b></b>")
    public Mono<?> checkPromo(@RequestParam("promoCode") String promoCode,@RequestParam("serviceName") String serviceId,@RequestParam("session_id") String session_id, ServerWebExchange serverWebExchange) {
        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7);
        return promoService.validatePromoCode(promoCode, jwtUtil.extractUsername(token),serviceId);

    }
    @PostMapping("payment")
    @Operation(summary = "verify otp code", description = "REQUIRED_ROLES: <b></b>")
    public Mono<PaymentDTO>makePayment(@RequestParam("card_id")String card_id, @RequestParam("session_id")String session_id){
        PaymentDTO paymentDTO = new PaymentDTO();
        return Mono.just(paymentDTO);
    }

}
