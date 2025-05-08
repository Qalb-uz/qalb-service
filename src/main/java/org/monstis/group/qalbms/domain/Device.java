package org.monstis.group.qalbms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("device")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    private Long id;

    @Column("phone_number")
    private String phoneNumber;

    @Column("device_id")
    private String deviceId;

    @Column("created_at")
    private Instant createdAt;


    @Column("device_name")
    private String deviceName;

    @Column("fcm_token")
    private String fcmToken;
}
