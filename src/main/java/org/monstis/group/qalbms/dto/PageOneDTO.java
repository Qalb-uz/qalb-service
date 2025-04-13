package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.monstis.group.qalbms.enums.Lang;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageOneDTO {
    private String firstName;
    private String age;
    private Lang lang;
}
