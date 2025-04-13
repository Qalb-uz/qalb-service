package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentDTO {

        private List<SearchResultDTO> content;
        private Integer count;
        private String prevKey;
        private String nextKey;

}
