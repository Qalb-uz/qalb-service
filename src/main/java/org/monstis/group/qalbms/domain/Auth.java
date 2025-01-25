package org.monstis.group.qalbms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value ="auth" )
public class Auth implements Serializable
{
    @Id
    private Long id;

    @Column("otp_code")
    private String otpCode;

    @Column("phone_number")
    private String phoneNumber;


    @Column("created_at")
    private Instant createdAt;

}
