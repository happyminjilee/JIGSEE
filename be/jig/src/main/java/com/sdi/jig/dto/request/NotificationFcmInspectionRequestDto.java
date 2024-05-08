package com.sdi.jig.dto.request;

import java.util.List;

public record NotificationFcmInspectionRequestDto(
        String notificationId,
        List<String> serialNos
) {

    public static NotificationFcmInspectionRequestDto from(String notificationId, List<String> serialNos){
        return new NotificationFcmInspectionRequestDto(notificationId, serialNos);
    }
}
