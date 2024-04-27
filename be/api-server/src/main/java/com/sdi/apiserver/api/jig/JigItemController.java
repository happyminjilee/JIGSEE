package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.jig.dto.request.JigItemAddRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemExchangeRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemSerialNoRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemUpdateStatusRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemIsUsableResponseDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemResponseDto;
import com.sdi.apiserver.util.CheckList;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sdi.apiserver.api.jig.dto.response.JigItemIsUsableResponseDto.*;

@RestController
@RequestMapping("/v1/jig-item")
public class JigItemController {

    @GetMapping()
    Response<JigItemResponseDto> findBySerialNo(@RequestParam(name = "serial-no") String serialNo) {
        JigItemResponseDto dto = new JigItemResponseDto(0L,
                "testModel",
                "testSerialNo",
                "WAREHOUSE",
                "testExpectLife",
                3,
                "testUseAccumulationTime",
                0,
                List.of(
                        new CheckList("test1", "test1"),
                        new CheckList("test2", "test2")
                ));
        return Response.success(dto);
    }

    @PostMapping()
    Response<Void> add(@RequestBody JigItemAddRequestDto dto) {
        return Response.success();
    }

    @GetMapping("/usable")
    Response<JigItemIsUsableResponseDto> isUsable(@RequestParam(name = "facility-model") String facilityModel,
                                                  @RequestParam(name = "jig-serial-no") String jigSerialNo) {
        JigItemIsUsableResponseDto dto = new JigItemIsUsableResponseDto(
                true,
                new JigItemSummary(
                        1,
                        "testUseAccumulationTime",
                        1
                )
        );

        return Response.success(dto);
    }

    @DeleteMapping()
    Response<Void> delete(@RequestBody JigItemSerialNoRequestDto dto) {
        return Response.success();
    }

    @PutMapping("/status")
    Response<Void> updateStatus(JigItemUpdateStatusRequestDto dto){
        return Response.success();
    }

    @PutMapping("/exchange")
    Response<Void> exchange(JigItemExchangeRequestDto dto){
        return Response.success();
    }
}
