package com.sdi.apiserver.api.wo;

import com.sdi.apiserver.api.wo.client.WorkOrderClient;
import com.sdi.apiserver.api.wo.dto.request.WorkOrderCreateRequest;
import com.sdi.apiserver.api.wo.dto.request.WorkOrderDoneRequestDto;
import com.sdi.apiserver.api.wo.dto.request.WorkOrderUpdateRequestDto;
import com.sdi.apiserver.api.wo.dto.request.WorkOrderUpdateStatusRequestDto;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderDetailResponseDto;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderGroupingResponseDto;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderResponseDto;
import com.sdi.apiserver.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/work-order")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderClient workOrderClient;

    @GetMapping("/detail")
    Response<WorkOrderDetailResponseDto> detail(@RequestParam(name = "work-order-id") Long workOrderId) {
        System.out.println("-----");
        return workOrderClient.detail(workOrderId);
    }

    @GetMapping("/all")
    Response<WorkOrderResponseDto> all(@RequestParam(name = "status") String status,
                                       @RequestParam(name = "page", required = false) Integer page,
                                       @RequestParam(name = "size", required = false) Integer size) {
        return workOrderClient.all(status, page, size);
    }

    @GetMapping("/grouping")
    Response<WorkOrderGroupingResponseDto> grouping() {
        return workOrderClient.grouping();
    }

    @GetMapping
    Response<WorkOrderResponseDto> searchByPerson(@RequestParam(name = "employee-no", required = false) String employeeNo,
                                                  @RequestParam(name = "name", required = false) String name) {
        return workOrderClient.searchByPerson(employeeNo, name);
    }

    @PostMapping
    Response<Void> add(@RequestBody WorkOrderCreateRequest dto) {
        return workOrderClient.add(dto);
    }

    @PutMapping("/tmp")
    Response<Void> tmp(@RequestBody WorkOrderUpdateRequestDto dto) {
        return workOrderClient.tmp(dto);
    }

    @PutMapping("/done")
    Response<Void> done(@RequestBody WorkOrderDoneRequestDto dto) {
        return workOrderClient.done(dto);
    }

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody WorkOrderUpdateStatusRequestDto dto) {
        return workOrderClient.updateStatus(dto);
    }
}
