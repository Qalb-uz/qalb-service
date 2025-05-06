package org.monstis.group.qalbms.service;



import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface PromoService {

    Mono<?> validatePromoCode(String promoCode, String username,String therapistId);
}
