package org.monstis.group.qalbms.impl;


import org.monstis.group.qalbms.domain.PromoCode;
import org.monstis.group.qalbms.dto.PromoCodeResponseDTO;
import org.monstis.group.qalbms.enums.PromoCodeErrorType;
import org.monstis.group.qalbms.repository.PromoCodeRepository;
import org.monstis.group.qalbms.repository.PromoCodeUsageRepository;
import org.monstis.group.qalbms.service.PromoService;
import org.monstis.group.qalbms.service.UserService;
import org.monstis.group.qalbms.utils.TypedResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PromoCodeServiceImpl implements PromoService {
    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeUsageRepository promoCodeUsageRepository;
    private final UserService userService;

    public PromoCodeServiceImpl(PromoCodeRepository promoCodeRepository, PromoCodeUsageRepository promoCodeUsageRepository, UserService userService) {
        this.promoCodeRepository = promoCodeRepository;
        this.promoCodeUsageRepository = promoCodeUsageRepository;
        this.userService = userService;
    }

    public Mono<PromoCodeResponseDTO> validatePromoCode(String promoCode, String userId, String serviceId) {
        return promoCodeRepository.findByCode(promoCode)
                .switchIfEmpty(Mono.error(new TypedResponseException(
                        HttpStatus.NOT_FOUND,
                        PromoCodeErrorType.PROMOCODE.name(),
                        "Промокод не найден"
                )))
                .flatMap(code -> {
                    PromoCodeResponseDTO responseDTO = new PromoCodeResponseDTO();

                    responseDTO.setCode(code.getCode());
                    responseDTO.setDescription(code.getDescription());
                    responseDTO.setId(String.valueOf(code.getId()));
                    if (code.isExpired()) {
                        return Mono.error(new TypedResponseException(
                                HttpStatus.BAD_REQUEST,
                                PromoCodeErrorType.PROMOCODE.name(),
                                "Срок действия промокода истёк"
                        ));
                    }

                    return promoCodeUsageRepository.countByPromoCodeIdAndUsername(code.getId(), userId)
                            .flatMap(usageCount -> {
                                if (code.getUsagePerUserLimit() != null && usageCount >= code.getUsagePerUserLimit()) {
                                    return Mono.error(new TypedResponseException(
                                            HttpStatus.BAD_REQUEST,
                                            PromoCodeErrorType.PROMOCODE.name(),
                                            "Этот промокод уже использован"
                                    ));
                                }

                                if (!code.getAllowedServicesList().contains(serviceId)) {
                                    return Mono.error(new TypedResponseException(
                                            HttpStatus.BAD_REQUEST,
                                            PromoCodeErrorType.PROMOCODE.name(),
                                            "Промокод нельзя применить к выбранной услуге"
                                    ));
                                }

                                if (Boolean.TRUE.equals(code.getFirstOrderOnly())) {
                                    return userService.isFirstOrder(userId)
                                            .flatMap(isFirst -> {
                                                if (!isFirst) {
                                                    return Mono.error(new TypedResponseException(
                                                            HttpStatus.BAD_REQUEST,
                                                            PromoCodeErrorType.PROMOCODE.name(),
                                                            "Этот промокод можно использовать только при первом заказе"
                                                    ));
                                                }
                                                return Mono.just(responseDTO); // Promo is valid
                                            });
                                }


                                return Mono.just(responseDTO); // Promo is valid
                            });

                })
                .onErrorResume(e -> {
                    if (e instanceof TypedResponseException) {
                        return Mono.error(e);
                    }
                    return Mono.error(new TypedResponseException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            PromoCodeErrorType.VERIFY_FAILED.name(),
                            "Не удалось проверить промокод"
                    ));
                });
    }





}
