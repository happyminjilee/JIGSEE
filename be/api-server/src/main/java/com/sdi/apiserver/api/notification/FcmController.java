package com.sdi.apiserver.api.notification;

import com.sdi.apiserver.api.request_response.client.NotificationApiClient;
import com.sdi.apiserver.api.request_response.dto.request.RepairJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.request.RequestJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.response.RepairJigDetailResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RepairJigListResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RequestJigDetailResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RequestJigListResponseDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification/fcm")
@RequiredArgsConstructor
public class FcmController {
    public static final String ACCESS_TOKEN_PREFIX = "Authorization";

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
