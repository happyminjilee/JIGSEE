package com.sdi.apiserver.api.notification;

import com.sdi.apiserver.api.notification.client.NotificationClient;
import com.sdi.apiserver.api.notification.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification/email")
@RequiredArgsConstructor
public class EmailController {
    public static final String ACCESS_TOKEN_PREFIX = "Authorization";
    private final NotificationClient notificationClient;

    @PostMapping("/subscribe")
    Response<Void> subscribeEmail(HttpServletRequest request) {
        notificationClient.subscribeEmail(getAccessToken(request));
        return Response.success();
    }
    @DeleteMapping("/unsubscribe")
    Response<Void> unsubscribeEmail(HttpServletRequest request) {
        notificationClient.unsubscribeEmail(getAccessToken(request));
        return Response.success();
    }

    @PostMapping("/inspection")
    Response<Void> sendInspectionEmail(@RequestBody NotificationFcmInspectionRequestDto dto) {
        notificationClient.sendInspectionEmail(dto);
        return Response.success();
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
