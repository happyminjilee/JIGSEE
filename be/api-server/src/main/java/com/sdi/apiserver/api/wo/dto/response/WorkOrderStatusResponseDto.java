package com.sdi.apiserver.api.wo.dto.response;

public record WorkOrderStatusResponseDto(
        int countRepairFinish,
        int countDelete,
        int countRepairing
) {
    public static WorkOrderStatusResponseDto of(int countRepairFinish, int countDelete, int countRepairing) {
        return new WorkOrderStatusResponseDto(countRepairFinish, countDelete, countRepairing);
    }
}
