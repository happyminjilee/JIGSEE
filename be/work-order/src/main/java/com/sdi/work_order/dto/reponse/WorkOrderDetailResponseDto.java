package com.sdi.work_order.dto.reponse;

import com.sdi.work_order.client.response.JigItemResponseDto;
import com.sdi.work_order.entity.WorkOrderRDBEntity;
import com.sdi.work_order.util.WorkOrderCheckItem;
import com.sdi.work_order.util.WorkOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record WorkOrderDetailResponseDto(
        Long id,
        WorkOrderStatus status,
        String creator,
        String terminator,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        JigItemInfo jigItemInfo,
        List<WorkOrderCheckItem> checkList

) {

    public static WorkOrderDetailResponseDto from(WorkOrderRDBEntity workOrderRDBEntity,
                                                  String creator,
                                                  String terminator,
                                                  JigItemResponseDto jigItemResponseDto,
                                                  List<WorkOrderCheckItem> workOrderCheckItem){
        return new WorkOrderDetailResponseDto(
                workOrderRDBEntity.getId(),
                workOrderRDBEntity.getStatus(),
                creator,
                terminator,
                workOrderRDBEntity.getCreatedAt(),
                workOrderRDBEntity.getUpdatedAt(),
                JigItemInfo.from(jigItemResponseDto),
                workOrderCheckItem
        );
    }


    public record JigItemInfo(
            String model,
            String serialNo,
            String status,
            String expectLife,
            Integer useCount,
            String useAccumulationTime,
            Integer repairCount
    ){
        public static JigItemInfo from(JigItemResponseDto dto){
            return new JigItemInfo(
                    dto.model(),
                    dto.serialNo(),
                    dto.status(),
                    dto.expectLife(),
                    dto.useCount(),
                    dto.useAccumulationTime(),
                    dto.repairCount()
            );
        }
    }
}
