package org.monstis.group.qalbms.repository;

import org.monstis.group.qalbms.domain.Payment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends R2dbcRepository<Payment, String> {
}
