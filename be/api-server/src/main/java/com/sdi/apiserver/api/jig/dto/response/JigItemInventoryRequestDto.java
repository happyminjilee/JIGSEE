package com.sdi.apiserver.api.jig.dto.response;

import java.util.List;

public record JigItemInventoryRequestDto(
        List<JigItemInventory> list
) {
    public record JigItemInventory(String model, String count){
    }
}
