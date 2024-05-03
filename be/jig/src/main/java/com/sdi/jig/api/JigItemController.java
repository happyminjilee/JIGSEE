package com.sdi.jig.api;

import com.sdi.jig.application.JigItemService;
import com.sdi.jig.dto.request.JigItemAddRequestDto;
import com.sdi.jig.dto.request.JigItemExchangeRequestDto;
import com.sdi.jig.dto.request.JigItemSerialNoRequestDto;
import com.sdi.jig.dto.request.JigItemUpdateStatusRequestDto;
import com.sdi.jig.dto.response.JigItemFacilityAvailableResponseDto;
import com.sdi.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.jig.dto.response.JigItemResponseDto;
import com.sdi.jig.util.Response;
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

    // 재고추가
    @PostMapping
    Response<Void> add(@RequestBody JigItemAddRequestDto dto) {
        jigItemService.add(dto.list());
        return Response.success();
    }

    @GetMapping("/usable")
    Response<JigItemIsUsableResponseDto> isUsable(@RequestParam(name = "facility-model") String facilityModel,
                                                  @RequestParam(name = "jig-serial-no") String jigSerialNo) {
        JigItemIsUsableResponseDto dto = jigItemService.isUsable(facilityModel, jigSerialNo);
        return Response.success(dto);
    }

    @DeleteMapping
    Response<Void> delete(@RequestBody JigItemSerialNoRequestDto dto) {
        jigItemService.deleteBySerialNo(dto.serialNo());
        return Response.success();
    }

    @PutMapping("/status")
    Response<Void> ready(@RequestBody JigItemUpdateStatusRequestDto dto) {
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
            @RequestParam(name = "facility-model") String facilityModel){
        return Response.success(jigItemService.facilityAvailable(facilityModel));
    }
}
