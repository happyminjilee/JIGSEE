package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.CommonRequest;
import com.sdi.apiserver.api.jig.dto.request.JigItemAddRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemExchangeRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemSerialNoRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemUpdateStatusRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemResponseDto;
import com.sdi.apiserver.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jig-item")
@RequiredArgsConstructor
@Slf4j
class JigItemController {

    @Value("${apis.jig-base-url}")
    private String jigBaseUrl;

    private final CommonRequest request;

    @GetMapping
    ResponseEntity<Response<JigItemResponseDto>> findBySerialNo(@RequestParam(name = "serial-no") String serialNo) {
        log.info("{}로 Jig-Item정보 조회", serialNo);
        String url = String.format("%s/v1/jig-item?serial-no=%s", jigBaseUrl, serialNo);
        return request.get(url);
    }

    @PostMapping()
    ResponseEntity<Response<Void>> add(@RequestBody JigItemAddRequestDto dto) {
        log.info("Jig-Item 데이터 삽입{}", dto.list());
        String url = String.format("%s/v1/jig-item", jigBaseUrl);
        return request.post(url, dto);
    }

    @GetMapping("/usable")
    ResponseEntity<Response<JigItemIsUsableResponseDto>> isUsable(@RequestParam(name = "facility-model") String facilityModel,
                                                  @RequestParam(name = "jig-serial-no") String jigSerialNo) {
        log.info("{}에 {}가 들어갈 수 있는지 확인", facilityModel, jigSerialNo);
        String url = String.format("%s/v1/jig-item/usable?facility-model=%s&jig-serial-no=%s", jigBaseUrl, facilityModel, jigSerialNo);
        return request.get(url);
    }

    @DeleteMapping()
    ResponseEntity<Response<Void>> delete(@RequestBody JigItemSerialNoRequestDto dto) {
        log.info("{} 삭제", dto.serialNo());
        String url = String.format("%s/v1/jig-item", jigBaseUrl);
        return request.delete(url, dto);
    }

    @PutMapping("/status")
    ResponseEntity<Response<Void>> updateStatus(@RequestBody JigItemUpdateStatusRequestDto dto){
        log.info("{} 상태 변경 {}", dto.serialNo(), dto.status());
        String url = String.format("%s/v1/jig-item/status", jigBaseUrl);
        return request.put(url, dto);
    }

    @PutMapping("/exchange")
    ResponseEntity<Response<Void>> exchange(@RequestBody JigItemExchangeRequestDto dto){
        log.info("{}에 {}를 {}로 변경", dto.facilitySerialNo(), dto.beforeSerialNo(), dto.afterSerialNo());
        String url = String.format("%s/v1/jig-item/exchange", jigBaseUrl);
        return request.put(url, dto);
    }

    @PutMapping("/recovery")
    ResponseEntity<Response<Void>> recovery(@RequestBody JigItemSerialNoRequestDto dto) {
        log.info("{} 상태 변경", dto.serialNo());
        String url = String.format("%s/v1/jig-item/recovery", jigBaseUrl);
        return request.put(url, dto);
    }
}
