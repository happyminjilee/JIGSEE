package com.sdi.notification.controller;

import com.sdi.notification.dto.MemberInfoDto;
import com.sdi.notification.dto.response.NotificationListResponseDto;
import com.sdi.notification.dto.response.UncheckedNotificationListResponseDto;
import com.sdi.notification.service.ApiService;
import com.sdi.notification.service.SearchService;
import com.sdi.notification.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
class SearchController {
    private final ApiService apiService;
    private final SearchService searchService;

    @GetMapping("/search/unchecked")
    Response<UncheckedNotificationListResponseDto> searchUnchecked(@RequestHeader("Authorization") String accessToken) {
        MemberInfoDto currentMember = apiService.getMember(accessToken);
        return Response.success(searchService.getUncheckedNotifications(currentMember));
    }

    @GetMapping("/search/all")
    Response<NotificationListResponseDto> searchAll(@RequestHeader("Authorization") String accessToken,
                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        MemberInfoDto currentMember = apiService.getMember(accessToken);
        return Response.success(searchService.getNotifications(currentMember, page, size));
    }

    @GetMapping("/check")
    Response<Void> checkNotification(@RequestParam("notification-id") Long notificationId) {
        searchService.setCheck(notificationId);
        return Response.success();
    }


}