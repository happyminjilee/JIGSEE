package com.sdi.apiserver.api.wo;

import com.sdi.apiserver.api.member.MemberController;
import com.sdi.apiserver.api.wo.client.WorkOrderClient;
import com.sdi.apiserver.api.wo.dto.request.*;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderDetailResponseDto;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderGroupingResponseDto;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderResponseDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/work-order")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderClient workOrderClient;
    private final MemberController memberController;

    @GetMapping("/detail")
    Response<WorkOrderDetailResponseDto> detail(HttpServletRequest request, @RequestParam(name = "work-order-id") Long workOrderId) {
        memberController.engineerCheck(request);
        return workOrderClient.detail(getAccessToken(request), workOrderId);
    }

    @GetMapping("/all")
    Response<WorkOrderResponseDto> all(HttpServletRequest request,
                                       @RequestParam(name = "status") String status,
                                       @RequestParam(name = "page", required = false) Integer page,
                                       @RequestParam(name = "size", required = false) Integer size) {
        memberController.engineerCheck(request);
        return workOrderClient.all(getAccessToken(request), status, page, size);
    }

    @GetMapping("/grouping")
    Response<WorkOrderGroupingResponseDto> grouping(HttpServletRequest request) {
        memberController.engineerCheck(request);
        return workOrderClient.grouping(getAccessToken(request));
    }

    @GetMapping
    Response<WorkOrderResponseDto> searchByPerson(HttpServletRequest request,
                                                  @RequestParam(name = "employee-no", required = false) String employeeNo,
                                                  @RequestParam(name = "name", required = false) String name) {
        memberController.engineerCheck(request);
        return workOrderClient.searchByPerson(getAccessToken(request), employeeNo, name);
    }

    @PostMapping
    Response<Void> add(HttpServletRequest request, @RequestBody WorkOrderCreateRequest dto) {
        memberController.engineerCheck(request);
        return workOrderClient.add(getAccessToken(request), dto);
    }

    @PutMapping("/tmp")
    Response<Void> tmp(HttpServletRequest request, @RequestBody WorkOrderUpdateRequestDto dto) {
        memberController.engineerCheck(request);
        return workOrderClient.tmp(dto);
    }

    @PutMapping("/done")
    Response<Void> save(HttpServletRequest request, @RequestBody WorkOrderDoneRequestDto dto) {
        memberController.engineerCheck(request);
        return workOrderClient.save(getAccessToken(request), dto);
    }

    @PutMapping("/status")
    Response<Void> updateStatus(HttpServletRequest request, @RequestBody WorkOrderUpdateStatusRequestDto dto) {
        memberController.engineerCheck(request);
        return workOrderClient.updateStatus(dto);
    }

    @PostMapping("/auto")
    Response<Void> autoCreate(@RequestBody WorkOrderAutoCreateRequestDto dto){
        return workOrderClient.autoCreate(dto);
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
