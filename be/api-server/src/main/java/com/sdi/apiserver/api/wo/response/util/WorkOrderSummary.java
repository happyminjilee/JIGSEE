package com.sdi.apiserver.api.wo.response.util;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WorkOrderSummary {

    Long id;
    String model;
    String serialNo;
    String creator;
    String terminator;
    Boolean isTerminate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
