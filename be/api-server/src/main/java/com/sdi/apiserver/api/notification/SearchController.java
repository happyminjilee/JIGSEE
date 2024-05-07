package com.sdi.apiserver.api.notification;

import com.sdi.apiserver.api.notification.client.NotificationClient;
import com.sdi.apiserver.api.notification.dto.response.NotificationListResponseDto;
import com.sdi.apiserver.api.notification.dto.response.UncheckedNotificationListResponseDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification/search")
@RequiredArgsConstructor
public class SearchController {
    public static final String ACCESS_TOKEN_PREFIX = "Authorization";
    private final NotificationClient notificationClient;
    @GetMapping("/unchecked")
    Response<UncheckedNotificationListResponseDto> searchUnchecked(HttpServletRequest request) {
        return notificationClient.searchUnchecked(getAccessToken(request));
    }

    @GetMapping("/all")
    Response<NotificationListResponseDto> searchAll(HttpServletRequest request,
                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return notificationClient.searchAll(getAccessToken(request), page, size);
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
