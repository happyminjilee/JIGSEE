package com.sdi.apiserver.api.jig.client;


import com.sdi.apiserver.api.jig.dto.request.*;
import com.sdi.apiserver.api.jig.dto.response.JigItemFacilityAvailableResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemInventoryRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "jigItemClient", url = "${apis.jig-item-base-url}")
public interface JigItemClient {

    @GetMapping()
    Response<JigItemResponseDto> findBySerialNo(@RequestParam(name = "serial-no") String serialNo);

    @GetMapping("/include-delete")
    Response<JigItemResponseDto> findBySerialNoIncludeDelete(@RequestParam(name = "serial-no") String serialNo);

    @PostMapping()
    Response<Void> add(@RequestBody JigItemAddRequestDto dto);

    @GetMapping("/usable")
    Response<JigItemIsUsableResponseDto> isUsable(@RequestParam(name = "facility-serial-no") String facilitySerialNo,
                                                  @RequestParam(name = "jig-serial-no") String jigSerialNo);

    @DeleteMapping()
    Response<Void> delete(@RequestBody JigItemSerialNoRequestDto dto);

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody JigItemUpdateStatusRequestDto dto);

    @PutMapping("/exchange")
    Response<Void> exchange(@RequestBody JigItemExchangeRequestDto dto);

    @PutMapping("/recovery")
    Response<Void> recovery(@RequestBody JigItemSerialNoRequestDto dto);

    @GetMapping("/facility-available")
    Response<JigItemFacilityAvailableResponseDto> facilityAvailable(
            @RequestParam(name = "facility-model") String facilityModel);

    @PostMapping("/inspection")
    Response<Void> inspection(@RequestBody JigItemInspectionRequestDto dto);

    @PutMapping("/accept")
    Response<Void> accept(@RequestHeader("Authorization") String accessToken, @RequestBody JigItemAcceptRequestDto dto);

    @GetMapping("/inventory")
    Response<JigItemInventoryRequestDto> inventory();

    @PutMapping("/repair")
    Response<Void> repair(@RequestBody JigItemRepairRequestDto dto);

    @PutMapping("/delete-and-repair")
    Response<Void> deleteAndRepair(@RequestBody JigItemDeleteAndRepairRequestDto dto);
}
