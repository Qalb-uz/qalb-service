package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.monstis.group.qalbms.enums.Gender;
import org.monstis.group.qalbms.enums.SessionFor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageTwoDTO {
    private Gender gender;
    private SessionFor sessionFor;
}
