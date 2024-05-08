package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.jig.client.JigClient;
import com.sdi.apiserver.api.jig.dto.request.JigUpdateRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigResponseDto;
import com.sdi.apiserver.api.member.MemberController;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jig")
@RequiredArgsConstructor
@Slf4j
class JigController {

    private final MemberController memberController;
    private final JigClient jigClient;

    @PutMapping
    Response<Void> update(HttpServletRequest request, @RequestBody JigUpdateRequestDto dto) {
        memberController.managerCheck(request);
        log.info("{}의 check list 수정", dto.getModel());
        return jigClient.update(dto);
    }

    @GetMapping
    Response<JigResponseDto> searchByModel(HttpServletRequest request, @RequestParam(name = "model") String model) {
        memberController.managerCheck(request);
        log.info("{} 정보 조회 요청", model);
        return jigClient.searchByModel(model);
    }
}
