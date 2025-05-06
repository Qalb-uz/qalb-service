package org.monstis.group.qalbms.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value ="client_session" )
public class Session {
    @Id
    private UUID id;

    private String session_for;
    private String price;
    private LocalDateTime time;
    private String therapistId;
    private String therapistName;
    private String therapistImage;
    private String therapistLastName;
    private String username;
}
