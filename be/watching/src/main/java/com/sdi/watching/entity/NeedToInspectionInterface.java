package com.sdi.watching.entity;

import java.time.LocalDateTime;

public interface NeedToInspectionInterface {
    Long getJigItemId();

    Long getJigId();

    Integer getRepairCount();

    Integer getOptimalIntervalDay();

    LocalDateTime getLastInOutTime();
}
