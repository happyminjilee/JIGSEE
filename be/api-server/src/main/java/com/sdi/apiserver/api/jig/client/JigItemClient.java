package com.sdi.apiserver.api.jig.client;


import com.sdi.apiserver.api.jig.dto.request.JigItemAddRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemExchangeRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemSerialNoRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemUpdateStatusRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "jigItemClient", url = "${apis.jig-item-base-url}")
public interface JigItemClient {

    @GetMapping()
    Response<JigItemResponseDto> findBySerialNo(@RequestParam(name = "serial-no") String serialNo);

    @PostMapping()
    Response<Void> add(@RequestBody JigItemAddRequestDto dto);

    @GetMapping("/usable")
    Response<JigItemIsUsableResponseDto> isUsable(@RequestParam(name = "facility-model") String facilityModel,
                                                                  @RequestParam(name = "jig-serial-no") String jigSerialNo);

    @DeleteMapping()
    Response<Void> delete(@RequestBody JigItemSerialNoRequestDto dto);

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody JigItemUpdateStatusRequestDto dto);

    @PutMapping("/exchange")
    Response<Void> exchange(@RequestBody JigItemExchangeRequestDto dto);

    @PutMapping("/recovery")
    Response<Void> recovery(@RequestBody JigItemSerialNoRequestDto dto);
}
