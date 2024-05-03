package com.sdi.apiserver.api.request_response;

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
@RequestMapping("/v1/request")
@RequiredArgsConstructor
public class RequestController {
    public static final String ACCESS_TOKEN_PREFIX = "Authorization";
    private final NotificationApiClient notificationApiClient;

    @PostMapping("/jig")
    Response<Void> jig(@RequestBody RequestJigRequestDto dto, HttpServletRequest request) {
        return notificationApiClient.makeWantRequest(getAccessToken(request), dto);
    }

    @PostMapping("/repair")
    Response<Void> repair(@RequestBody RepairJigRequestDto dto, HttpServletRequest request) {
        return notificationApiClient.makeRepairRequest(getAccessToken(request), dto);
    }

    @GetMapping("/jig/all")
    Response<RequestJigListResponseDto> all(@RequestParam(name = "filter") String filter,
                                            @RequestParam(name = "page", defaultValue = "1") int page,
                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        return notificationApiClient.findAllWantJigRequests(filter, page, size);
    }

    @GetMapping("/jig/detail")
    Response<RequestJigDetailResponseDto> detailRequestJig(@RequestParam(name = "request-jig-id") String id) {
        return notificationApiClient.findOneJigRequest(id);
    }

    @GetMapping("/repair")
    Response<RepairJigListResponseDto> repairRequestJig(@RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return notificationApiClient.findAllRepairRequests(page, size);
    }

    @GetMapping("/repair/detail")
    Response<RepairJigDetailResponseDto> detailRepairRequestJig(@RequestParam(name = "repair-jig-id") String id){
        return notificationApiClient.findOneRepairRequest(id);
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
