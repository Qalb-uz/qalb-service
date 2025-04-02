package org.monstis.group.qalbms.dto;

public record CommonResponse(
        Cause cause,
        Boolean success
) {
}