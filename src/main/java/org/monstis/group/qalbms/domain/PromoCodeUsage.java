package org.monstis.group.qalbms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("promo_code_usages")
public class PromoCodeUsage {

    @Id
    private Long id;

    @Column("promo_code_id")
    private Long promoCodeId;

    @Column("username") // or "phone_number" if preferred
    private String username;

    @Column("used_at")
    private LocalDateTime usedAt;

    // Constructors, Getters, and Setters
}
