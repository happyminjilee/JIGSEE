package com.sdi.work_order.util;

import com.sdi.work_order.entity.WorkOrderRDBEntity;

import java.time.LocalDateTime;

public record WorkOrderItem(
        Long id,
        String model,
        String serialNo,
        String creator,
        String terminator,
        WorkOrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ){
        public static WorkOrderItem from(WorkOrderRDBEntity entity, String creator, String terminator){
            return new WorkOrderItem(
                    entity.getId(),
                    entity.getModel(),
                    entity.getJigSerialNo(),
                    entity.getCreatorEmployeeNo(),
                    entity.getTerminatorEmployeeNo(),
                    entity.getStatus(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
        }
    }