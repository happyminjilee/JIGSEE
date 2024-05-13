package com.sdi.apiserver.api.request_response.client;

import com.sdi.apiserver.api.request_response.dto.request.RepairJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.request.RequestJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.request.ResponseJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.response.RepairJigDetailResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RepairJigListResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RequestJigDetailResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RequestJigListResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
@FeignClient(name = "notificationApiClient", url = "${apis.notification-api-base-url}")
public interface NotificationApiClient {
    final String ACCESS_TOKEN_PREFIX = "Authorization";
    @PostMapping("/request/jig")
    Response<Void> makeWantRequest(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                                   @RequestBody RequestJigRequestDto dto);

    @PostMapping("/response/jig")
    Response<Void> makeWantResponse(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                                    @RequestBody ResponseJigRequestDto dto);

    @PostMapping("/request/repair")
    Response<Void> makeRepairRequest(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                                     @RequestBody RepairJigRequestDto dto);

    @GetMapping("/request/jig/all")
    Response<RequestJigListResponseDto> findAllWantJigRequests(@RequestParam(value = "filter",defaultValue = "ALL") String filter,
                                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size);

    @GetMapping("/request/jig/detail")
    Response<RequestJigDetailResponseDto> findOneJigRequest(@RequestParam(value = "request-jig-id") String requestId);

    @GetMapping("/request/repair")
    Response<RepairJigListResponseDto> findAllRepairRequests(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size);

    @GetMapping("/request/repair/detail")
    Response<RepairJigDetailResponseDto> findOneRepairRequest(@RequestParam(value = "repair-jig-id") String requestId);

}
