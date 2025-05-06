package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.monstis.group.qalbms.domain.Card;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private String id;
    private  String date;
    private Card card;
    private SessionDTO session;
    private SessionTherapistDTO therapist;
}
