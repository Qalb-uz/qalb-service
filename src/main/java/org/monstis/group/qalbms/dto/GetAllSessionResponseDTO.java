package org.monstis.group.qalbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSessionResponseDTO  {

    private SessionTopicDTO session;
    private SessionTherapistDTO therapist;


}
