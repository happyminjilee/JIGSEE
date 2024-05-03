package com.sdi.apiserver.api.notification.client;

import com.sdi.apiserver.api.notification.dto.request.MessageRequestDto;
import com.sdi.apiserver.api.notification.dto.response.NotificationListResponseDto;
import com.sdi.apiserver.api.notification.dto.response.UncheckedNotificationListResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@FeignClient(name = "notificationClient", url = "${apis.notification-base-url}")
public interface NotificationClient {
    final String ACCESS_TOKEN_PREFIX = "Authorization";

    @GetMapping(value = "notification/sse/subscribe", produces = "text/event-stream")
    SseEmitter subscribe(@RequestHeader(ACCESS_TOKEN_PREFIX) String accessToken);

    @PostMapping(value = "notification/sse/send-message")
    Response<Void> sendMessage(@RequestBody MessageRequestDto messageRequestDto);

    @DeleteMapping("notification/sse/disconnect")
    Response<Void> disconnect(@RequestHeader(ACCESS_TOKEN_PREFIX) String accessToken);

    @GetMapping("notification/search/unchecked")
    Response<UncheckedNotificationListResponseDto> searchUnchecked(@RequestHeader(ACCESS_TOKEN_PREFIX) String accessToken);

    @GetMapping("notification/search/all")
    Response<NotificationListResponseDto> searchAll(@RequestHeader(ACCESS_TOKEN_PREFIX) String accessToken,
                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size);


}
