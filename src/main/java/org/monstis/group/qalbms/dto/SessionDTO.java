package org.monstis.group.qalbms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private LocalDateTime time = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
    private String therapistId="ByQHVpYBTeEC6r8p5ca2";


}


