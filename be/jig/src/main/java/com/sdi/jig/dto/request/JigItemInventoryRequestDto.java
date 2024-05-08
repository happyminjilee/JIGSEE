package com.sdi.jig.dto.request;

import com.sdi.jig.entity.rdb.JigItemRDBEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record JigItemInventoryRequestDto(
        List<JigItemInventory> list
) {

    public static JigItemInventoryRequestDto from(Map<String, List<JigItemRDBEntity>> map){
        List<JigItemInventory> list = map.keySet()
                .stream()
                .map(key -> JigItemInventory.of(key, map.get(key).size()))
                .toList();

        return new JigItemInventoryRequestDto(list);
    }

    public record JigItemInventory(
            String model,
            Integer count
    ){
        private static JigItemInventory of(String mode, Integer count){
            return new JigItemInventory(mode, count);
        }
    }
}
