package com.sdi.apiserver.api.wo.client;

import com.sdi.apiserver.api.wo.dto.request.WorkOrderCreateRequest;
import com.sdi.apiserver.api.wo.dto.request.WorkOrderDoneRequestDto;
import com.sdi.apiserver.api.wo.dto.request.WorkOrderUpdateRequestDto;
import com.sdi.apiserver.api.wo.dto.request.WorkOrderUpdateStatusRequestDto;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderDetailResponseDto;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderGroupingResponseDto;
import com.sdi.apiserver.api.wo.dto.response.WorkOrderResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "workOrderClient", url = "${apis.work-order-base-url}")
public interface WorkOrderClient {

    @GetMapping("/detail")
    Response<WorkOrderDetailResponseDto> detail(@RequestParam(name = "work-order-id") Long workOrderId);

    @GetMapping
    Response<WorkOrderResponseDto> all(@RequestParam(name = "status") String status,
                                       @RequestParam(name = "page", required = false) Integer page,
                                       @RequestParam(name = "size", required = false) Integer size);

    @GetMapping("/grouping")
    Response<WorkOrderGroupingResponseDto> grouping();

    @GetMapping
    Response<WorkOrderResponseDto> searchByPerson(@RequestParam(name = "employee-no", required = false) String employeeNo,
                                                  @RequestParam(name = "name", required = false) String name);

    @PostMapping
    Response<Void> add(@RequestBody WorkOrderCreateRequest dto);

    @PutMapping("/tmp")
    Response<Void> tmp(@RequestBody WorkOrderUpdateRequestDto dto);

    @PutMapping("/done")
    Response<Void> done(@RequestBody WorkOrderDoneRequestDto dto);

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody WorkOrderUpdateStatusRequestDto dto);
}
