package com.sdi.apiserver.api.notification;

import com.sdi.apiserver.api.notification.client.NotificationClient;
import com.sdi.apiserver.api.notification.dto.request.MessageRequestDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notification/sse")
@RequiredArgsConstructor
public class SseController {
    public static final String ACCESS_TOKEN_PREFIX = "Authorization";
    private final NotificationClient notificationClient;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    SseEmitter subscribe(HttpServletRequest request) {
        return notificationClient.subscribe(getAccessToken(request));
    }

    @PostMapping(value = "/send-message")
    Response<Void> sendMessage(@RequestBody MessageRequestDto messageRequestDto) {
        return notificationClient.sendMessage(messageRequestDto);
    }

    @DeleteMapping("/disconnect")
    Response<Void> disconnect(HttpServletRequest request) {
        return notificationClient.disconnect(getAccessToken(request));
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
