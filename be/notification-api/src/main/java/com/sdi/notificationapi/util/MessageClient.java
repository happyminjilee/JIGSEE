package com.sdi.notificationapi.util;

import com.sdi.notificationapi.dto.MessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "messageClient", url = "${apis.api-base-url}") // 요청을 보낼 url 작성
public interface MessageClient {
    @PostMapping("/notification/sse/send-message")
    void sendMessage(@RequestBody MessageDto messageDto, @RequestHeader("Authorization") String accessToken);
}
