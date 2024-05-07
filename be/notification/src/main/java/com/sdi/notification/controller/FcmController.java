package com.sdi.notification.controller;

import com.sdi.notification.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.notification.dto.request.FcmTokenRequestDto;
import com.sdi.notification.service.FcmService;
import com.sdi.notification.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification/fcm")
@RequiredArgsConstructor
class FcmController {
    private final FcmService fcmService;
    private final String ACCESS_TOKEN_PREFIX = "Authorization";
    @PostMapping("/token")
    Response<Void> saveToken(@RequestHeader(ACCESS_TOKEN_PREFIX) String accessToken, @RequestBody FcmTokenRequestDto fcmTokenRequestDto) {
        fcmService.saveToken(accessToken, fcmTokenRequestDto);
        return Response.success();
    }

    @PostMapping("/inspection")
    Response<Void> sendNotification(@RequestBody NotificationFcmInspectionRequestDto notificationFcmInspectionRequestDto) {
        fcmService.sendNotificationTo(notificationFcmInspectionRequestDto);
        return Response.success();
    }
}
