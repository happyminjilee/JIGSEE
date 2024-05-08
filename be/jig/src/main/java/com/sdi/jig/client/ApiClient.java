package com.sdi.jig.client;

import com.sdi.jig.dto.request.JigItemAcceptRequestDto;
import com.sdi.jig.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.jig.dto.request.WorkOrderAutoCreateRequestDto;
import com.sdi.jig.util.TokenHeader;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "apiClient", url = "${apis.api-base-url}")
public interface ApiClient {

    @PostMapping("/work-order/auto")
    void createWorkOrder(@RequestBody WorkOrderAutoCreateRequestDto dto);

    @PostMapping("/response/jig")
    void accept(@RequestHeader(name = TokenHeader.AUTHORIZATION) String accessToken,
                @RequestBody JigItemAcceptRequestDto dto);

    @PostMapping("/notification/fcm/inspection")
    void inspection(@RequestBody NotificationFcmInspectionRequestDto dto);
}
