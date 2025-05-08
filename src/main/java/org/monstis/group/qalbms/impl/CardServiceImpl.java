package org.monstis.group.qalbms.impl;

import org.monstis.group.qalbms.domain.Card;
import org.monstis.group.qalbms.dto.CardDTO;
import org.monstis.group.qalbms.dto.OtpResponse;
import org.monstis.group.qalbms.repository.CardRepository;
import org.monstis.group.qalbms.service.CardService;
import org.monstis.group.qalbms.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CardServiceImpl implements CardService {
    private final AuthImpl  authImpl;
    private final JwtUtil jwtUtil;
    private final CardRepository cardRepository;

    public CardServiceImpl(AuthImpl authImpl, JwtUtil jwtUtil, CardRepository cardRepository) {
        this.jwtUtil = jwtUtil;
        this.authImpl = authImpl;
        this.cardRepository = cardRepository;
    }

    @Override
    public Mono<OtpResponse> addCard(CardDTO cardDTO, String username, String token) {
        return cardRepository.findByPanAndCardPhoneNumber(cardDTO.getPan(),username)
                .flatMap(cardOld -> {
                    // Card exists, just send OTP
                    return authImpl.registerPhone(
                            username,
                            jwtUtil.extractDeviceName(token),
                            jwtUtil.extractDeviceId(token)
                    ).map(ResponseEntity::getBody);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    Card card = new Card();
                    card.setPan(cardDTO.getPan());
                    card.setExpDate(cardDTO.getExpDate());
                    card.setCardHolderName("Test User");
                    card.setCardPhoneNumber(username);

                    if (cardDTO.getPan().startsWith("9860")) {
                        card.setCardBrand("HUMO");
                        card.setCardLogo("https://github.com/Qalb-uz/qalb-service/blob/main/Type%3DHumo.png?raw=true");
                    } else if (cardDTO.getPan().startsWith("8600")) {
                        card.setCardBrand("UZCARD");
                        card.setCardLogo("https://github.com/Qalb-uz/qalb-service/blob/main/Type%3DUzCard.png?raw=true");
                    } else {
                        card.setCardBrand("UNKNOWN");
                        card.setCardLogo("https://github.com/Qalb-uz/qalb-service/blob/main/Type%3DInstallment.png?raw=true");
                    }

                    return cardRepository.save(card)
                            .flatMap(savedCard ->
                                    authImpl.registerPhone(
                                            username,
                                            jwtUtil.extractDeviceName(token),
                                            jwtUtil.extractDeviceId(token)
                                    ).map(ResponseEntity::getBody) // ✅ extract only body
                            );
                }));
    }




}



