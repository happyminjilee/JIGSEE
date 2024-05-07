package com.sdi.apiserver.api.wo;

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

    @GetMapping("/detail")
    Response<WorkOrderDetailResponseDto> detail(@RequestParam(name = "work-order-id") Long workOrderId, HttpServletRequest request) {
        return workOrderClient.detail(getAccessToken(request), workOrderId);
    }

    @GetMapping("/all")
    Response<WorkOrderResponseDto> all(@RequestParam(name = "status") String status,
                                       @RequestParam(name = "page", required = false) Integer page,
                                       @RequestParam(name = "size", required = false) Integer size,
                                       HttpServletRequest request) {
        return workOrderClient.all(getAccessToken(request), status, page, size);
    }

    @GetMapping("/grouping")
    Response<WorkOrderGroupingResponseDto> grouping(HttpServletRequest request) {
        return workOrderClient.grouping(getAccessToken(request));
    }

    @GetMapping
    Response<WorkOrderResponseDto> searchByPerson(HttpServletRequest request,
                                                  @RequestParam(name = "employee-no", required = false) String employeeNo,
                                                  @RequestParam(name = "name", required = false) String name) {
        return workOrderClient.searchByPerson(getAccessToken(request), employeeNo, name);
    }

    @PostMapping
    Response<Void> add(HttpServletRequest request, @RequestBody WorkOrderCreateRequest dto) {
        return workOrderClient.add(getAccessToken(request), dto);
    }

    @PutMapping("/tmp")
    Response<Void> tmp(@RequestBody WorkOrderUpdateRequestDto dto) {
        return workOrderClient.tmp(dto);
    }

    @PutMapping("/done")
    Response<Void> save(HttpServletRequest request, @RequestBody WorkOrderDoneRequestDto dto) {
        return workOrderClient.save(getAccessToken(request), dto);
    }

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody WorkOrderUpdateStatusRequestDto dto) {
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
