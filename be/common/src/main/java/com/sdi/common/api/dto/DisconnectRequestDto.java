package com.sdi.common.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisconnectRequestDto(
        String role,
        @JsonProperty("employee-no")
        String employeeNo
) {
}
