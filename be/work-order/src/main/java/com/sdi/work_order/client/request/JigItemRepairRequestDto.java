package com.sdi.work_order.client.request;

public record JigItemRepairRequestDto(
        String serialNo
) {

    public static JigItemRepairRequestDto from(String serialNo){
        return new JigItemRepairRequestDto(serialNo);
    }
}
