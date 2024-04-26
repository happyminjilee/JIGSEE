package com.sdi.common.dto;

import com.sdi.common.entity.JigItemEntity;
import com.sdi.common.util.JigStatus;

public record JigItemDto(
        Long id,
        String serialNo,
        JigStatus status,
        Long useAccumulateTime
) {
    public static JigItemDto from(JigItemEntity jigItemEntity) {
        return new JigItemDto(
                jigItemEntity.getId(),
                jigItemEntity.getSerialNo(),
                jigItemEntity.getStatus(),
                jigItemEntity.getUseAccumulateTime());
    }
}
