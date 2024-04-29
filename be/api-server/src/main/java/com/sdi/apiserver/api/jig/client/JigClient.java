package com.sdi.apiserver.api.jig.client;


import com.sdi.apiserver.api.jig.dto.request.JigUpdateRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jigClient", url = "${apis.jig-base-url}")
public interface JigClient {

    @PutMapping
    Response<Void> update(@RequestBody JigUpdateRequestDto dto);

    @GetMapping
    Response<JigResponseDto> searchByModel(@RequestParam(name = "model") String model);
}
