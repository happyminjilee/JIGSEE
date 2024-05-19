package com.sdi.apiserver.api.wo.dto.response.util;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WorkOrderSummary {

    Long id;
    String model;
    String serialNo;
    String creator;
    String terminator;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
