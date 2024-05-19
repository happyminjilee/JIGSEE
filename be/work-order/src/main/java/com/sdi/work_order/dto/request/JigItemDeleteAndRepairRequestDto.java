package com.sdi.work_order.dto.request;

public record JigItemDeleteAndRepairRequestDto(
        String serialNo,
        Boolean isAllPass
) {

    public static JigItemDeleteAndRepairRequestDto of(String serialNo, Boolean isAllPass){
        return new JigItemDeleteAndRepairRequestDto(serialNo, isAllPass);
    }
}
