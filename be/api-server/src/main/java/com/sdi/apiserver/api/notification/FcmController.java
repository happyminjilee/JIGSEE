package com.sdi.apiserver.api.notification;

import com.sdi.apiserver.api.notification.client.NotificationClient;
import com.sdi.apiserver.api.notification.dto.request.FcmTokenRequestDto;
import com.sdi.apiserver.api.notification.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification/fcm")
@RequiredArgsConstructor
public class FcmController {
    public static final String ACCESS_TOKEN_PREFIX = "Authorization";
    private final NotificationClient notificationClient;

    @PostMapping("/token")
    Response<Void> saveToken(HttpServletRequest request, @RequestBody FcmTokenRequestDto fcmTokenRequestDto) {
        notificationClient.saveToken(getAccessToken(request), fcmTokenRequestDto);
        return Response.success();
    }

    @PostMapping("/inspection")
    Response<Void> sendFcmNotification(@RequestBody NotificationFcmInspectionRequestDto notificationFcmInspectionRequestDto) {
        notificationClient.sendInspectionNotification(notificationFcmInspectionRequestDto);
        return Response.success();
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
