package com.sdi.jig.client;

import com.sdi.jig.dto.request.JigItemAcceptRequestDto;
import com.sdi.jig.dto.response.RequestCountRepairResponseDto;
import com.sdi.jig.util.Response;
import com.sdi.jig.util.TokenHeader;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notificationApiClient", url = "${apis.api-base-url}")
public interface NotificationApiClient {

    @PostMapping("/response/jig")
    void accept(@RequestHeader(name = TokenHeader.AUTHORIZATION) String accessToken,
                @RequestBody JigItemAcceptRequestDto dto);

    @GetMapping("/request/count/repair")
    Response<RequestCountRepairResponseDto> countRepairRequest(@RequestHeader(name = TokenHeader.AUTHORIZATION) String accessToken,
                                                               @RequestParam(name = "year") Integer year,
                                                               @RequestParam(name = "month") Integer month);
}

