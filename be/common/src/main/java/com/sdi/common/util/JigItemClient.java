package com.sdi.common.util;

import com.sdi.common.dto.JigItemResponseDto;
import com.sdi.common.dto.JigItemStatusUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "jigItemClient", url = "http://localhost:8081") // 요청을 보낼 url 작성
public interface JigItemClient {
    @PutMapping("/jig-item/status")
    void changeJigStatusToReady(@RequestBody JigItemStatusUpdateDto jigItemStatusUpdateDto);
    @GetMapping("/jig-item?serial-no={serialNo}")
    JigItemResponseDto findBySerialNo(@PathVariable("serialNo") String serialNo);
}
