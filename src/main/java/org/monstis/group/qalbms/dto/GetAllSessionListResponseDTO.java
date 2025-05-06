package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSessionListResponseDTO {

    private List<GetAllSessionResponseDTO> sessions;
    private Integer prevKey;
    private Integer nextKey;
    private Integer count;

}