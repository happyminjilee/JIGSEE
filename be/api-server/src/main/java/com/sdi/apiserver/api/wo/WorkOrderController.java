package com.sdi.apiserver.api.wo;

import com.sdi.apiserver.api.wo.request.WorkOrderCreateRequest;
import com.sdi.apiserver.api.wo.request.WorkOrderDoneRequestDto;
import com.sdi.apiserver.api.wo.response.WorkOrderDetailResponseDto;
import com.sdi.apiserver.api.wo.response.WorkOrderResponseDto;
import com.sdi.apiserver.api.wo.request.WorkOrderUpdateRequestDto;
import com.sdi.apiserver.util.CheckDoneList;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/work-order")
public class WorkOrderController {

    @GetMapping("/detail")
    Response<WorkOrderDetailResponseDto> searchBySerialNo(@RequestParam(name = "serial-no") String serialNo) {
        WorkOrderDetailResponseDto dto = new WorkOrderDetailResponseDto(
                0L,
                "testCreator",
                "testTerminator",
                "testModel",
                "testSerialNo",
                List.of(
                        new CheckDoneList("testContent", "testStandard", "testUuid", "testMeasure", "testInspection", "testMemo", true),
                        new CheckDoneList("testContent", "testStandard", "testUuid", "testMeasure", "testInspection", "testMemo", false)
                )
        );
        return Response.success(dto);
    }

    @GetMapping("/all")
    Response<WorkOrderResponseDto> all() {
        WorkOrderResponseDto dto = new WorkOrderResponseDto(
                0L,
                "testModel",
                "testSerialNo",
                "testCreator",
                "testTerminator",
                true,
                LocalDateTime.now(),
                LocalDateTime.now());
        return Response.success(dto);
    }

    @GetMapping("/writing")
    Response<WorkOrderResponseDto> notComplete() {
        WorkOrderResponseDto dto = new WorkOrderResponseDto(
                0L,
                "testModel",
                "testSerialNo",
                "testCreator",
                "testTerminator",
                true,
                LocalDateTime.now(),
                LocalDateTime.now());
        return Response.success(dto);
    }

    @GetMapping()
    Response<WorkOrderResponseDto> searchByPerson(@RequestParam(name = "employee-no") String employeeNo,
                                  @RequestParam(name = "name") String name) {
        WorkOrderResponseDto dto = new WorkOrderResponseDto(
                0L,
                "testModel",
                "testSerialNo",
                "testCreator",
                "testTerminator",
                true,
                LocalDateTime.now(),
                LocalDateTime.now());
        return Response.success(dto);
    }

    @PostMapping()
    Response<Void> add(@RequestBody WorkOrderCreateRequest dto) {
        return Response.success();
    }

    @PutMapping("/tmp")
    Response<Void> tmp(@RequestBody WorkOrderUpdateRequestDto dto) {
        return Response.success();
    }

    @PutMapping("/done")
    Response<Void> done(@RequestBody WorkOrderDoneRequestDto dto) {
        return Response.success();
    }
}
