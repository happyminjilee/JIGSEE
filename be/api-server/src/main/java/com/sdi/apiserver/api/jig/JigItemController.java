package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.jig.dto.request.JigItemCreateRequestDto;
import com.sdi.apiserver.api.jig.dto.request.JigItemSerialNoRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigItemResponseDto;
import com.sdi.apiserver.util.CheckList;
import com.sdi.apiserver.api.jig.dto.util.JigStatus;
import com.sdi.apiserver.util.CheckType;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/jig-item")
public class JigItemController {

    @GetMapping()
    Response<JigItemResponseDto> search(@RequestParam(name = "model") String model,
                                        @RequestParam(name = "serial-no") String serialNo) {
        JigItemResponseDto dto = new JigItemResponseDto(0L,
                "testModel",
                "testSerialNo",
                JigStatus.WAREHOUSE,
                "testExpectLife",
                CheckType.UBM,
                0,
                0,
                List.of(
                        new CheckList("test1", "test1"),
                        new CheckList("test2", "test2")
                ));
        return Response.success(dto);
    }

    @PostMapping()
    Response<Void> add(@RequestBody JigItemCreateRequestDto dto) {
        return Response.success();
    }

    @GetMapping("/adjustment")
    Response<Void> adjust(@RequestParam(name = "facility-no") String facilityNo,
                          @RequestParam(name = "serial-no") String serialNo) {
        return Response.success();
    }

    @DeleteMapping()
    Response<Void> delete(@RequestBody JigItemSerialNoRequestDto dto) {
        return Response.success();
    }

    @PutMapping("/use-waiting")
    Response<Void> useWaiting(@RequestBody JigItemSerialNoRequestDto dto) {
        return Response.success();
    }

    @PutMapping("/warehouse")
    Response<Void> wareHouse(@RequestBody JigItemSerialNoRequestDto dto) {
        return Response.success();
    }

    @PutMapping("/change")
    Response<Void> change(@RequestBody JigItemSerialNoRequestDto dto) {
        return Response.success();
    }
}
