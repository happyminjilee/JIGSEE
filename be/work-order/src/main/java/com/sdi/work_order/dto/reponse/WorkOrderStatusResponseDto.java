package com.sdi.work_order.dto.reponse;

public record WorkOrderStatusResponseDto(
        int countRepairFinish,
        int countDelete,
        int countRepairing
) {
    public static WorkOrderStatusResponseDto of(int countRepairFinish, int countDelete, int countRepairing) {
        return new WorkOrderStatusResponseDto(countRepairFinish, countDelete, countRepairing);
    }
}
