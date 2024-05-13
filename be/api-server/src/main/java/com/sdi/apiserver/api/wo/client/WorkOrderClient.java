package com.sdi.apiserver.api.wo.client;

import com.sdi.apiserver.api.wo.dto.request.*;
import com.sdi.apiserver.api.wo.dto.response.*;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "workOrderClient", url = "${apis.work-order-base-url}")
public interface WorkOrderClient {

    String ACCESS_TOKEN_PREFIX = "Authorization";

    @GetMapping("/detail")
    Response<WorkOrderDetailResponseDto> detail(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                                                @RequestParam(name = "work-order-id") Long workOrderId);

    @GetMapping("/all")
    Response<WorkOrderResponseDto> all(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                                       @RequestParam(name = "status") String status,
                                       @RequestParam(name = "page", required = false) Integer page,
                                       @RequestParam(name = "size", required = false) Integer size);

    @GetMapping("/grouping")
    Response<WorkOrderGroupingResponseDto> grouping(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken);

    @GetMapping
    Response<WorkOrderResponseDto> searchByPerson(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                                                  @RequestParam(name = "employee-no", required = false) String employeeNo,
                                                  @RequestParam(name = "name", required = false) String name);

    @PostMapping
    Response<Void> add(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                       @RequestBody WorkOrderCreateRequest dto);

    @PutMapping("/tmp")
    Response<Void> tmp(@RequestBody WorkOrderUpdateRequestDto dto);

    @PutMapping("/done")
    Response<WorkOrderDoneResponseDto> save(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                                            @RequestBody WorkOrderDoneRequestDto dto);

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody WorkOrderUpdateStatusRequestDto dto);

    @PostMapping("/auto")
    Response<Void> autoCreate(@RequestBody WorkOrderAutoCreateRequestDto dto);

    @GetMapping("/member/status")
    Response<WorkOrderStatusResponseDto> getStatus(@RequestHeader(name = ACCESS_TOKEN_PREFIX) String accessToken,
                                                   @RequestParam(name = "year") Integer year,
                                                   @RequestParam(name = "month") Integer month);

    @GetMapping("/count/repair-request")
    Response<WorkOrderCountResponseDto> countRepairRequest(@RequestParam(name = "year", required = false) Integer year,
                                                           @RequestParam(name = "month", required = false) Integer month);
}
