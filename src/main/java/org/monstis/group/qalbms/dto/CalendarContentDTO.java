package org.monstis.group.qalbms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarContentDTO {
    private List<SessionCalendar>  content;

    private String prevKey;
    private String nextKey;
    private String count;

}
