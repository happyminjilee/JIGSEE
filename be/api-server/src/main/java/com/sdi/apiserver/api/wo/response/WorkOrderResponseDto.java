package com.sdi.apiserver.api.wo.response;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WorkOrderResponseDto {
    Long id;
    String model;
    String serialNo;
    String creator;
    String terminator;
    Boolean isTerminate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
