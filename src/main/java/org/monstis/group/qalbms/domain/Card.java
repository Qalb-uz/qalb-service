package org.monstis.group.qalbms.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Long id;
    private String pan;
    private String expDate;
    private String cardLogo;
    private String cardHolderName;
    private String cardBrand;
    private String cardPhoneNumber;

}
