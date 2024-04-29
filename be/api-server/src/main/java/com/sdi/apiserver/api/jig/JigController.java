package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.CommonRequest;
import com.sdi.apiserver.api.jig.dto.request.JigUpdateRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigResponseDto;
import com.sdi.apiserver.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jig")
@RequiredArgsConstructor
@Slf4j
class JigController {

    @Value("${apis.jig-base-url}")
    private String jigBaseUrl;

    private final CommonRequest request;

    @PutMapping
    ResponseEntity<Response<Void>> update(@RequestBody JigUpdateRequestDto dto) {
        log.info("{}의 check list 수정", dto.getModel());
        String url = String.format("%s/v1/jig", jigBaseUrl);
        return request.put(url, dto);
    }

    @GetMapping
    public ResponseEntity<Response<JigResponseDto>> searchByModel(@RequestParam(name = "model") String model) {
        log.info("{} 정보 조회 요청", model);
        String url = String.format("%s/v1/jig?model=%s", jigBaseUrl, model);
        return request.get(url);
    }
}
