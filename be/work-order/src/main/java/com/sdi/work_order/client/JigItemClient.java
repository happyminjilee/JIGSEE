package com.sdi.work_order.client;

import com.sdi.work_order.client.request.JigItemDeleteRequestDto;
import com.sdi.work_order.client.request.JigItemRepairRequestDto;
import com.sdi.work_order.client.response.JigItemResponseDto;
import com.sdi.work_order.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "jigItemClient", url = "${apis.api-base-url}")
public interface JigItemClient {

    String SERIAL_NO = "serial-no";

    @GetMapping("/jig-item")
    Response<JigItemResponseDto> findBySerialNo(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken,
                                                @RequestParam(name = SERIAL_NO) String serialNo);

    @GetMapping("/jig-item/include-delete")
    Response<JigItemResponseDto> findBySerialNoIncludeDelete(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken,
                                                @RequestParam(name = SERIAL_NO) String serialNo);

    @DeleteMapping("/jig-item")
    void deleteBySerialNo(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken,
                          @RequestBody JigItemDeleteRequestDto dto);

    @PutMapping("/repair")
    void repair(@RequestBody JigItemRepairRequestDto dto);
}
