package com.sdi.jig.api;

import com.sdi.jig.application.JigItemService;
import com.sdi.jig.dto.request.*;
import com.sdi.jig.dto.response.JigItemFacilityAvailableResponseDto;
import com.sdi.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.jig.dto.response.JigItemResponseDto;
import com.sdi.jig.util.Response;
import com.sdi.jig.util.TokenHeader;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jig-item")
@RequiredArgsConstructor
class JigItemController {

    private final JigItemService jigItemService;

    @GetMapping
    Response<JigItemResponseDto> findBySerialNo(@RequestParam(name = "serial-no") String serialNo) {
        return Response.success(jigItemService.findBySerialNo(serialNo));
    }

    @PostMapping
    Response<Void> add(@RequestBody JigItemAddRequestDto dto) {
        jigItemService.add(dto.list());
        return Response.success();
    }

    @GetMapping("/usable")
    Response<JigItemIsUsableResponseDto> isUsable(@RequestParam(name = "facility-serial-no") String facilitySerialNo,
                                                  @RequestParam(name = "jig-serial-no") String jigSerialNo) {
        JigItemIsUsableResponseDto dto = jigItemService.isUsable(facilitySerialNo, jigSerialNo);
        return Response.success(dto);
    }

    @DeleteMapping
    Response<Void> delete(@RequestBody JigItemSerialNoRequestDto dto) {
        jigItemService.deleteBySerialNo(dto.serialNo());
        return Response.success();
    }

    @PutMapping("/status")
    Response<Void> status(@RequestBody JigItemUpdateStatusRequestDto dto) {
        jigItemService.updateStateBySerialNo(dto.serialNo(), dto.status());
        return Response.success();
    }

    @PutMapping("/exchange")
    Response<Void> exchange(@RequestBody JigItemExchangeRequestDto dto) {
        jigItemService.exchangeBySerialNo(dto.facilitySerialNo(), dto.beforeSerialNo(), dto.afterSerialNo());
        return Response.success();
    }

    @PutMapping("/recovery")
    Response<Void> recovery(@RequestBody JigItemSerialNoRequestDto dto) {
        jigItemService.recoveryBySerialNo(dto.serialNo());
        return Response.success();
    }

    @GetMapping("/facility-available")
    Response<JigItemFacilityAvailableResponseDto> facilityAvailable(
            @RequestParam(name = "facility-model") String facilityModel) {
        return Response.success(jigItemService.facilityAvailable(facilityModel));
    }

    @PostMapping("/inspection")
    Response<Void> inspection(@RequestBody JigItemInspectionRequestDto dto) {
        jigItemService.jigItemInspection(dto.serialNos());
        return Response.success();
    }

    @PutMapping("/accept")
    Response<Void> accept(@RequestBody JigItemAcceptRequestDto dto, HttpServletRequest request) {
        String accessToken = request.getHeader(TokenHeader.AUTHORIZATION);
        jigItemService.accept(accessToken, dto);
        return Response.success();
    }

    @GetMapping("inventory")
    Response<JigItemInventoryRequestDto> inventory() {
        JigItemInventoryRequestDto dto = jigItemService.inventory();
        return Response.success(dto);
    }
}
