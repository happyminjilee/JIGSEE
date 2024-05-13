package com.sdi.apiserver.api.request_response;

import com.sdi.apiserver.api.member.MemberController;
import com.sdi.apiserver.api.request_response.client.NotificationApiClient;
import com.sdi.apiserver.api.request_response.dto.request.RepairJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.request.RequestJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.response.*;
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
    private final MemberController memberController;

    @PostMapping("/jig")
    Response<Void> jig(HttpServletRequest request, @RequestBody RequestJigRequestDto dto) {
        memberController.engineerCheck(request);
        return notificationApiClient.makeWantRequest(getAccessToken(request), dto);
    }

    @PostMapping("/repair")
    Response<Void> repair(HttpServletRequest request, @RequestBody RepairJigRequestDto dto) {
        memberController.producerCheck(request);
        return notificationApiClient.makeRepairRequest(getAccessToken(request), dto);
    }

    @GetMapping("/jig/all")
    Response<RequestJigListResponseDto> all(HttpServletRequest request,
                                            @RequestParam(name = "filter") String filter,
                                            @RequestParam(name = "page", defaultValue = "1") int page,
                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        memberController.engineerCheck(request);
        return notificationApiClient.findAllWantJigRequests(filter, page, size);
    }

    @GetMapping("/jig/detail")
    Response<RequestJigDetailResponseDto> detailRequestJig(HttpServletRequest request,
                                                           @RequestParam(name = "request-jig-id") String id) {
        memberController.engineerCheck(request);
        return notificationApiClient.findOneJigRequest(id);
    }

    @GetMapping("/repair")
    Response<RepairJigListResponseDto> repairRequestJig(HttpServletRequest request,
                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        memberController.producerCheck(request);
        return notificationApiClient.findAllRepairRequests(page, size);
    }

    @GetMapping("/repair/detail")
    Response<RepairJigDetailResponseDto> detailRepairRequestJig(HttpServletRequest request,
                                                                @RequestParam(name = "repair-jig-id") String id){
        memberController.producerCheck(request);
        return notificationApiClient.findOneRepairRequest(id);
    }

    @GetMapping("/count/repair")
    Response<RequestCountRepairResponseDto> countRepair(HttpServletRequest request,
                                                        @RequestParam(name = "year", required = false) Integer year,
                                                        @RequestParam(name = "month", required = false) Integer month) {
        memberController.producerCheck(request);
        return notificationApiClient.countRepair(year, month);
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
