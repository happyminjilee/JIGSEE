package com.sdi.watching.dto.request;

import java.util.List;

public record ClientSerialNosRequestDto(
        List<Long> serialNos
) {

    public static ClientSerialNosRequestDto from(List<Long> serialNos){
        return new ClientSerialNosRequestDto(serialNos);
    }
}
