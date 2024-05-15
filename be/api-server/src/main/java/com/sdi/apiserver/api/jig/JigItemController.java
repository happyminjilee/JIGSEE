package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.jig.client.JigItemClient;
import com.sdi.apiserver.api.jig.dto.request.*;
import com.sdi.apiserver.api.jig.dto.response.JigItemFacilityAvailableResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemInventoryRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemResponseDto;
import com.sdi.apiserver.api.member.MemberController;
import com.sdi.apiserver.util.HeaderUtils;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jig-item")
@RequiredArgsConstructor
@Slf4j
class JigItemController {

    private final MemberController memberController;
    private final JigItemClient jigItemClient;

    @GetMapping
    Response<JigItemResponseDto> findBySerialNo(HttpServletRequest request, @RequestParam(name = "serial-no") String serialNo) {
        // memberController.producerCheck(request);
        log.info("{}로 Jig-Item정보 조회", serialNo);
        return jigItemClient.findBySerialNo(serialNo);
    }

    @GetMapping("/include-delete")
    Response<JigItemResponseDto> findBySerialNoIncludeDelete(@RequestParam(name = "serial-no") String serialNo) {
        // memberController.producerCheck(request);
        log.info("{}로 Jig-Item정보 조회", serialNo);
        return jigItemClient.findBySerialNoIncludeDelete(serialNo);
    }

    @PostMapping
    Response<Void> add(HttpServletRequest request, @RequestBody JigItemAddRequestDto dto) {
        memberController.managerCheck(request);
        log.info("Jig-Item 데이터 삽입{}", dto.list());
        return jigItemClient.add(dto);
    }

    @GetMapping("/usable")
    Response<JigItemIsUsableResponseDto> isUsable(HttpServletRequest request,
                                                  @RequestParam(name = "facility-serial-no") String facilitySerialNo,
                                                  @RequestParam(name = "jig-serial-no") String jigSerialNo) {
        memberController.producerCheck(request);
        log.info("{}에 {}가 들어갈 수 있는지 확인", facilitySerialNo, jigSerialNo);
        return jigItemClient.isUsable(facilitySerialNo, jigSerialNo);
    }

    @DeleteMapping()
    Response<Void> delete(HttpServletRequest request, @RequestBody JigItemSerialNoRequestDto dto) {
        memberController.producerCheck(request);
        log.info("{} 삭제", dto.serialNo());
        return jigItemClient.delete(dto);
    }

    @PutMapping("/status")
    Response<Void> updateStatus(HttpServletRequest request, @RequestBody JigItemUpdateStatusRequestDto dto) {
        log.info("{} 상태 변경 {}", dto.serialNo(), dto.status());
        return jigItemClient.updateStatus(dto);
    }

    @PutMapping("/exchange")
    Response<Void> exchange(HttpServletRequest request, @RequestBody JigItemExchangeRequestDto dto) {
        memberController.producerCheck(request);
        log.info("{}에 {}를 {}로 변경", dto.facilitySerialNo(), dto.beforeSerialNo(), dto.afterSerialNo());
        return jigItemClient.exchange(dto);
    }

    @PutMapping("/recovery")
    Response<Void> recovery(HttpServletRequest request, @RequestBody JigItemSerialNoRequestDto dto) {
        memberController.engineerCheck(request);
        log.info("{} 상태 변경", dto.serialNo());
        return jigItemClient.recovery(dto);
    }

    @GetMapping("/facility-available")
    Response<JigItemFacilityAvailableResponseDto> facilityAvailable(
            HttpServletRequest request,
            @RequestParam(name = "facility-model") String facilityModel) {
        memberController.engineerCheck(request);
        log.info("{}에 사용 가능한 지그 목록", facilityModel);
        return jigItemClient.facilityAvailable(facilityModel);
    }

    @PostMapping("/inspection")
    Response<Void> inspection(@RequestBody JigItemInspectionRequestDto dto) {
        log.info("{} 점검 항목 등록 요청", dto);
        return jigItemClient.inspection(dto);
    }

    @PutMapping("/accept")
    Response<Void> accept(HttpServletRequest request, @RequestBody JigItemAcceptRequestDto dto) {
        memberController.managerCheck(request);
        String accessToken = HeaderUtils.getAccessToken(request);
        log.info("{} 불출 승인 요청", dto);
        return jigItemClient.accept(accessToken, dto);
    }

    @GetMapping("/inventory")
    Response<JigItemInventoryRequestDto> inventory() {
        log.info("모델별 재고 요청");
        return jigItemClient.inventory();
    }

    @PutMapping("/repair")
    Response<Void> repair(@RequestBody JigItemRepairRequestDto dto){
        log.info("{} 수리 이력 생성 요청", dto.serialNo());
        return jigItemClient.repair(dto);
    }

    @PutMapping("/delete-and-repair")
    Response<Void> deleteAndRepair(@RequestBody JigItemDeleteAndRepairRequestDto dto){
        log.info("{} 상태에 따른 삭제 및 수리 이력 생성. 상태 : {}", dto.serialNo(), dto.isAllPass());
        return jigItemClient.deleteAndRepair(dto);
    }
}
