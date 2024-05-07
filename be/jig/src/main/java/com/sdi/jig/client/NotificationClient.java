package com.sdi.jig.client;

import com.sdi.jig.dto.request.NotificationFcmInspectionRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationClient", url = "${apis.api-base-url}")
public interface NotificationClient {

    @PostMapping("/notification/fcm/inspection")
    void inspection(@RequestBody NotificationFcmInspectionRequestDto dto);
}

