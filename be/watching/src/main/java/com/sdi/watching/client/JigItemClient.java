package com.sdi.watching.client;

import com.sdi.watching.dto.request.ClientJigItemIdsRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "jigItemClient", url = "${apis.api-base-url}")
public interface JigItemClient {

    @PostMapping("/jig-item/inspection")
    void inspection(@RequestBody ClientJigItemIdsRequestDto dto);
}
