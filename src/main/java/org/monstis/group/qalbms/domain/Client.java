package org.monstis.group.qalbms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.monstis.group.qalbms.dto.VerifyDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Table("client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private Long id;

    @Column("device_name")
    private String deviceName;
    @Column("device_id")
    private String deviceId;
    @Column("first_name")
    private String firstName;
    @Column("msisdn")
    private String msisdn;
    @Column("first_login_at")
    private Instant firstLoginAt;
    @Column("resume_id")
    private String resumeId;
    @Column("resume_status")
    private String resumeStatus;
}
