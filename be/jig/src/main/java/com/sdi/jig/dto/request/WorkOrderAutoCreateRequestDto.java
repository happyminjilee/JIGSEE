package com.sdi.jig.dto.request;

import java.util.List;

public record WorkOrderAutoCreateRequestDto(
        List<String> serialNos
) {
    public static WorkOrderAutoCreateRequestDto from(List<String> serialNos){
        return new WorkOrderAutoCreateRequestDto(serialNos);
    }
}
