package com.sdi.work_order.dto.reponse;

public record WorkOrderDoneResponseDto(
        Boolean passOrNot
) {

    public static WorkOrderDoneResponseDto from(boolean passOrNot){
        return new WorkOrderDoneResponseDto(passOrNot);
    }
}
