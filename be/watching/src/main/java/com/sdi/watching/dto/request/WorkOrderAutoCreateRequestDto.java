package com.sdi.watching.dto.request;

import java.util.List;

public record WorkOrderAutoCreateRequestDto(
        List<Long> serialNos
) {

    public static WorkOrderAutoCreateRequestDto from(List<Long> serialNos){
        return new WorkOrderAutoCreateRequestDto(serialNos);
    }
}
