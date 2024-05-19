package com.sdi.work_order.dto.request;

import java.util.List;

public record WorkOrderAutoCreateRequestDto(
        List<String> serialNos
) {
}
