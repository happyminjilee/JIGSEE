package com.sdi.apiserver.api.wo.dto.response;

import com.sdi.apiserver.api.jig.dto.response.JigItemResponseDto;
import com.sdi.apiserver.util.WorkOrderCheckItem;

import java.time.LocalDateTime;
import java.util.List;

public record WorkOrderDetailResponseDto(
        Long id,
        String status,
        String creator,
        String terminator,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        JigItemResponseDto jigItemInfo,
        List<WorkOrderCheckItem> checkList
) {
}
