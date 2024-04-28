package com.sdi.common.util;

import com.sdi.common.dto.MessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "apiClient", url = "http://localhost:8084") // 요청을 보낼 url 작성
public interface ApiClient {
    @PostMapping("/sse/send-message")
    void sendMessage(@RequestBody MessageDto messageDto);
}
