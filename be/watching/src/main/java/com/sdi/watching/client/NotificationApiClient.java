package com.sdi.watching.client;


import com.sdi.watching.dto.request.ClientJigItemIdsRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationApiClient", url = "${apis.api-base-url}")
public interface NotificationApiClient {

    @PostMapping("/notification/fcm/inspection")
    void inspection(@RequestBody ClientJigItemIdsRequestDto dto);
}
