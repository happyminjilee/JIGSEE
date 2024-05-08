package com.sdi.notification.controller;

import com.sdi.notification.dto.request.NotificationFcmInspectionRequestDto;
import com.sdi.notification.service.EmailService;
import com.sdi.notification.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification/email")
@RequiredArgsConstructor
public class EmailController {
    private final String ACCESS_TOKEN_PREFIX = "Authorization";
    private final EmailService emailService;
    @GetMapping("/test")
    Response<Void> sendEmail() {
        emailService.sendSimpleEmail();
        return Response.success();
    }

    @PostMapping("/subscribe")
    Response<Void> subscribe(HttpServletRequest request) {
        emailService.subscribe(getAccessToken(request));
        return Response.success();
    }
    @DeleteMapping("/unsubscribe")
    Response<Void> unsubscribe(HttpServletRequest request) {
        emailService.unsubscribe(getAccessToken(request));
        return Response.success();
    }

    @PostMapping("/inspection")
    Response<Void> sendInspectionEmail(@RequestBody NotificationFcmInspectionRequestDto dto) {
        emailService.sendInspectionEmail(dto);
        return Response.success();
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
