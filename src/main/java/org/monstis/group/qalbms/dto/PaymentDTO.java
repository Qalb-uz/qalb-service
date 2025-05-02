package org.monstis.group.qalbms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Integer  card_id=1;
    private Integer  session_id=1;
    private String  therapist_id="ByQHVpYBTeEC6r8p5ca2";
    private Integer  sessionCost=200000;
}
