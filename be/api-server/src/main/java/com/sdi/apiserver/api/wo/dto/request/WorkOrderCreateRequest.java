package com.sdi.apiserver.api.wo.dto.request;

import lombok.Value;


public record WorkOrderCreateRequest(
    String serialNo
) {
}
