package com.sdi.work_order.util;

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
        public static WorkOrderItem from(Long id,
                                         String model,
                                         String serialNo,
                                         String creator,
                                         String terminator,
                                         WorkOrderStatus status,
                                         LocalDateTime createdAt,
                                         LocalDateTime updatedAt){
            return new WorkOrderItem(id, model, serialNo, creator, terminator, status, createdAt, updatedAt);
        }
    }