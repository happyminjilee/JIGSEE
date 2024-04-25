package com.sdi.common.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisconnectRequestDto(
        String role,
        @JsonProperty("employee-no")
        String employeeNo
) {
}
