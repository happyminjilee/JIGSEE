package com.sdi.apiserver.api.wo.dto.request;

import java.util.List;

public record WorkOrderAutoCreateRequestDto(
        List<String> serialNos
) {
}
