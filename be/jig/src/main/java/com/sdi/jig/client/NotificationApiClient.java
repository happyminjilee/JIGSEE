package com.sdi.jig.client;

import com.sdi.jig.dto.request.JigItemAcceptRequestDto;
import com.sdi.jig.util.TokenHeader;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "notificationApiClient", url = "${apis.api-base-url}")
public interface NotificationApiClient {

    @PostMapping("/response/jig")
    void accept(@RequestHeader(name = TokenHeader.AUTHORIZATION) String accessToken,
                @RequestBody JigItemAcceptRequestDto dto);
}

