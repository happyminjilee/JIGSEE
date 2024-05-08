package com.sdi.work_order.client;

import com.sdi.work_order.client.request.JigItemDeleteRequestDto;
import com.sdi.work_order.client.response.JigItemResponseDto;
import com.sdi.work_order.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jigItemClient", url = "${apis.api-base-url}")
public interface JigItemClient {

    String SERIAL_NO = "serial-no";

    @GetMapping("/jig-item")
    Response<JigItemResponseDto> findBySerialNo(@RequestParam(name = SERIAL_NO) String serialNo);

    @DeleteMapping("/jig-item")
    void deleteBySerialNo(@RequestBody JigItemDeleteRequestDto dto);
}
