package com.sdi.jig.client;

import com.sdi.jig.dto.request.NotificationFcmInspectionRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationApiClient", url = "${apis.api-base-url}")
public interface NotificationApiClient {

    @PostMapping("/notification/fcm/inspection")
    void inspection(@RequestBody NotificationFcmInspectionRequestDto dto);
}

