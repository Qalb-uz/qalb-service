package org.monstis.group.qalbms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("payment")
public class Payment {
    @Id
    private String id;

    private  String date;
    private String price;
    private String card_id;
    private String session_id;
    private String therapist_id;
}
