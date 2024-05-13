package com.sdi.apiserver.api.jig.client;


import com.sdi.apiserver.api.jig.dto.request.JigUpdateRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigModelCountResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigMonthResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigUpdatedCheckListResponseDto;
import com.sdi.apiserver.api.jig.dto.util.JigStatus;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jigClient", url = "${apis.jig-base-url}")
public interface JigClient {

    @PutMapping
    Response<Void> update(@RequestBody JigUpdateRequestDto dto);

    @GetMapping
    Response<JigResponseDto> searchByModel(@RequestParam(name = "model") String model);

    @GetMapping("/status")
    Response<JigMonthResponseDto> monthStatus(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken,
                                              @RequestParam(name = "year", required = false) Integer year,
                                              @RequestParam(name = "month", required = false) Integer month);

    @GetMapping("/count")
    Response<JigModelCountResponseDto> jigCountStatus(@RequestParam(name = "status") JigStatus status);

    @GetMapping("/update-check-list")
    Response<JigUpdatedCheckListResponseDto> updatedCheckList(@RequestParam(name = "year", required = false) Integer year,
                                                              @RequestParam(name = "month", required = false) Integer month);
}
