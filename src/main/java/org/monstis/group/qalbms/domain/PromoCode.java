package org.monstis.group.qalbms.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("promo_codes")
public class PromoCode {

    @Id
    private Long id;

    @Column("code")
    private String code;

    @Column("description")
    private String description;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("active")
    private Boolean active;

    @Column("first_order_only")
    private Boolean firstOrderOnly;

    @Column("allowed_therapist_id") // Stored as comma-separated string
    private String allowedTherapistId; // e.g., "therapy,coaching"

    @Column("usage_limit")
    private Integer usageLimit;

    @Column("usage_per_user_limit")
    private Integer usagePerUserLimit;

    // Constructors, Getters, and Setters

    public boolean isExpired() {
        return endDate != null && endDate.isBefore(LocalDateTime.now());
    }

    public boolean isActiveNow() {
        return Boolean.TRUE.equals(active) && !isExpired();
    }

    // Add convenience method if needed to split allowedServices
    public List<String> getAllowedServicesList() {
        return allowedTherapistId != null ? List.of(allowedTherapistId.split(",")) : List.of();
    }
}
