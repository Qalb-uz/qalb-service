package org.monstis.group.qalbms.service;


import org.monstis.group.qalbms.dto.CardDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CardService {

    Flux<?> addCard(CardDTO cardDTO, String  username, String token);
}
