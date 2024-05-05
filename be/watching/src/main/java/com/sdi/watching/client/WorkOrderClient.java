package com.sdi.watching.client;

import com.sdi.watching.dto.request.WorkOrderAutoCreateRequestDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "workOrderClient", url = "${apis.api-base-url}")
public interface WorkOrderClient {

    @GetMapping("/work-order/auto")
    void create(@RequestBody WorkOrderAutoCreateRequestDto dto);
}
