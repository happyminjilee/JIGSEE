package com.sdi.jig.api;

import com.sdi.jig.application.JigItemService;
import com.sdi.jig.dto.request.JigItemAddRequestDto;
import com.sdi.jig.dto.request.JigItemSerialNoRequestDto;
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
    Response<JigItemResponseDto> findBySerialNo(@RequestParam(name = "serial-no") String serialNo){
        return Response.success(jigItemService.findBySerialNo(serialNo));
    }

    // 재고추가
    @PostMapping
    Response<Void> add(@RequestBody JigItemAddRequestDto dto){
        jigItemService.add(dto.list());
        return Response.success();
    }

    @GetMapping("/usable")
    Response<JigItemIsUsableResponseDto> isUsable(@RequestParam(name = "facility-model") String facilityModel,
                                                  @RequestParam(name = "jig-serial-no") String jigSerialNo){
        JigItemIsUsableResponseDto dto = jigItemService.isUsable(facilityModel, jigSerialNo);
        return Response.success(dto);
    }

    @DeleteMapping
    Response<Void> delete(@RequestBody JigItemSerialNoRequestDto dto){
        jigItemService.deleteBySerialNo(dto.serialNo());
        return Response.success();
    }
}
