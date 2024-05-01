package com.sdi.notificationapi.util;

import com.sdi.notificationapi.dto.JigItemResponseDto;
import com.sdi.notificationapi.dto.JigItemStatusUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "jigItemClient", url = "${apis.api-base-url}") // 요청을 보낼 url 작성
public interface JigItemClient {
    @PutMapping("/jig-item/status")
    void changeJigStatusToReady(@RequestBody JigItemStatusUpdateDto jigItemStatusUpdateDto, @RequestHeader("Authorization") String accessToken);
    @GetMapping("/jig-item?serial-no={serialNo}")
    JigItemResponseDto findBySerialNo(@PathVariable("serialNo") String serialNo, @RequestHeader("Authorization") String accessToken);
}
