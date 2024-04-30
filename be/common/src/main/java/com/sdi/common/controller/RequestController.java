package com.sdi.common.controller;

import com.sdi.common.dto.request.RepairJigRequestDto;
import com.sdi.common.dto.request.RequestJigRequestDto;
import com.sdi.common.dto.request.ResponseJigRequestDto;
import com.sdi.common.dto.response.RepairJigDetailResponseDto;
import com.sdi.common.dto.response.RepairJigListResponseDto;
import com.sdi.common.dto.response.RequestJigDetailResponseDto;
import com.sdi.common.dto.response.RequestJigListResponseDto;
import com.sdi.common.service.RequestService;
import com.sdi.common.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
class RequestController {
    private final RequestService requestService;

    @PostMapping("/request/jig")
    Response<Void> makeWantRequest(@RequestBody RequestJigRequestDto requestJigRequestDto, @RequestHeader("Authorization") String accessToken) {
        requestService.createWantRequest(requestJigRequestDto, accessToken);
        return Response.success();
    }

    @PostMapping("/response/jig")
    Response<Void> makeWantResponse(@RequestBody ResponseJigRequestDto responseJigRequestDto, @RequestHeader("Authorization") String accessToken) {
        requestService.createWantResponse(responseJigRequestDto, accessToken);
        return Response.success();
    }

    @PostMapping("/request/repair")
    Response<Void> makeRepairRequest(@RequestBody RepairJigRequestDto repairJigRequestDto, @RequestHeader("Authorization") String accessToken) {
        requestService.createRepairRequest(repairJigRequestDto, accessToken);
        return Response.success();
    }

    @GetMapping("/request/jig/all")
    Response<RequestJigListResponseDto> findAllWantJigRequests(@RequestParam(value = "filter",defaultValue = "ALL") String option,
                                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return Response.success(requestService.findAllWantJigRequests(option, page, size));
    }

    @GetMapping("/request/jig/detail")
    Response<RequestJigDetailResponseDto> findOneJigRequest(@RequestParam(value = "request-jig-id") String requestId) {
        return Response.success(requestService.findOneJigRequest(requestId));
    }

    @GetMapping("/request/repair")
    Response<RepairJigListResponseDto> findAllRepairRequests(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return Response.success(requestService.findAllRepairRequests(page, size));
    }

    @GetMapping("/request/repair/detail")
    Response<RepairJigDetailResponseDto> findOneRepairRequest(@RequestParam(value = "repair-jig-id") String requestId) {
        return Response.success(requestService.findOneRepairRequest(requestId));
    }
}
