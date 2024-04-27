package com.sdi.apiserver.api.request_response.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record RepairJigDetailResponseDto(
        Long id,
        String from,
        LocalDateTime createdAt,
        List<RepairJigDetailJigitem> serialNos
) {
    public static RepairJigDetailResponseDto from(Long id,
                                                  String from,
                                                  LocalDateTime createdAt,
                                                  List<RepairJigDetailJigitem> list){
        return new RepairJigDetailResponseDto(id, from, createdAt, list);

    }

    public record RepairJigDetailJigitem(
        String model,
        String serialNo
    ){
        public static RepairJigDetailJigitem from(String model, String serialNo){
            return new RepairJigDetailJigitem(model, serialNo);
        }
    }
}
