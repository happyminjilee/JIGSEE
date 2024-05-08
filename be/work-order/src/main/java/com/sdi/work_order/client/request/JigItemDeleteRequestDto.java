package com.sdi.work_order.client.request;

public record JigItemDeleteRequestDto(
        String serialNo
) {
    public static JigItemDeleteRequestDto from(String serialNo){
        return new JigItemDeleteRequestDto(serialNo);
    }
}
