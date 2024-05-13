package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.jig.client.JigClient;
import com.sdi.apiserver.api.jig.dto.request.JigUpdateRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigModelCountResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigMonthResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigUpdatedCheckListResponseDto;
import com.sdi.apiserver.api.jig.dto.util.JigStatus;
import com.sdi.apiserver.api.member.MemberController;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

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

    @GetMapping("/status")
    Response<JigMonthResponseDto> monthStatus(HttpServletRequest request,
                                              @RequestParam(name = "year", required = false) Integer year,
                                              @RequestParam(name = "month", required = false) Integer month) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        memberController.producerCheck(request);
        log.info("{}년 {}월 현황판 정보 조회 요청", year, month);
        return jigClient.monthStatus(accessToken, year, month);
    }

    @GetMapping("/count")
    Response<JigModelCountResponseDto> jigCountStatus(HttpServletRequest request,
                                                      @RequestParam(name = "status") JigStatus status) {
        memberController.producerCheck(request);
        log.info("{} 모델별 상태 갯수 조회 요청", status);
        return jigClient.jigCountStatus(status);
    }

    @GetMapping("/update-check-list")
    Response<JigUpdatedCheckListResponseDto> updatedCheckList(HttpServletRequest request,
                                                              @RequestParam(name = "year", required = false) Integer year,
                                                              @RequestParam(name = "month", required = false) Integer month) {
        memberController.producerCheck(request);
        log.info("{}년 {}월 WO 현황 조회 요청", year, month);
        return jigClient.updatedCheckList(year, month);
    }
}
