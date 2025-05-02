package org.monstis.group.qalbms.service;

import org.monstis.group.qalbms.repository.PromoCodeUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final PromoCodeUsageRepository orderRepository;

    @Autowired
    public UserService(PromoCodeUsageRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Mono<Boolean> isFirstOrder(String userId) {
        return orderRepository.countByUsername(userId) // Count the number of orders for this user
            .map(orderCount -> orderCount == 0); // If no orders exist, it's the first order
    }
}
