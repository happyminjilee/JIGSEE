package com.sdi.apiserver.api.request_response;

import com.sdi.apiserver.api.member.MemberController;
import com.sdi.apiserver.api.request_response.client.NotificationApiClient;
import com.sdi.apiserver.api.request_response.dto.request.ResponseJigRequestDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/response")
@RequiredArgsConstructor
public class ResponseController {
    private static final String ACCESS_TOKEN_PREFIX = "Authorization";
    private final NotificationApiClient notificationApiClient;
    private final MemberController memberController;

    @PostMapping("/jig")
    Response<Void> jig(HttpServletRequest request, @RequestBody ResponseJigRequestDto dto){
        memberController.managerCheck(request);
        return notificationApiClient.makeWantResponse(getAccessToken(request), dto);
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }

}
