package com.sdi.jig.client;

import com.sdi.jig.dto.request.WorkOrderAutoCreateRequestDto;
import com.sdi.jig.dto.response.WorkOrderCountResponseDto;
import com.sdi.jig.util.Response;
import com.sdi.jig.util.TokenHeader;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "workOrderClient", url = "${apis.api-base-url}")
public interface WorkOrderClient {

    @GetMapping("/work-order/count/repair-request")
    Response<WorkOrderCountResponseDto> countRepairRequest(@RequestHeader(name = TokenHeader.AUTHORIZATION) String accessToken,
                                                           @RequestParam(name = "year") Integer year,
                                                           @RequestParam(name = "month") Integer month);

    @PostMapping("/work-order/auto")
    Response<Void> auto(WorkOrderAutoCreateRequestDto dto);
}
