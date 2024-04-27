package com.sdi.apiserver.api.wo;

import com.sdi.apiserver.api.wo.request.WorkOrderCreateRequest;
import com.sdi.apiserver.api.wo.request.WorkOrderDoneRequestDto;
import com.sdi.apiserver.api.wo.request.WorkOrderUpdateStatusRequestDto;
import com.sdi.apiserver.api.wo.response.WorkOrderDetailResponseDto;
import com.sdi.apiserver.api.wo.response.WorkOrderGroupingResponseDto;
import com.sdi.apiserver.api.wo.response.WorkOrderResponseDto;
import com.sdi.apiserver.api.wo.response.util.WorkOrderSummary;
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
                "PUBLISH",
                "testCreator",
                "testTerminator",
                "testModel",
                "testSerialNo",
                LocalDateTime.now(),
                List.of(
                        new CheckDoneList("testContent", "testStandard", "testUuid", "testMeasure", "testMemo", true),
                        new CheckDoneList("testContent", "testStandard", "testUuid", "testMeasure", "testMemo", false)
                )
        );
        return Response.success(dto);
    }

    @GetMapping("/all")
    Response<WorkOrderResponseDto> all(@RequestParam(name = "status")String status) {
        WorkOrderSummary dto = new WorkOrderSummary(
                0L,
                "testModel",
                "testSerialNo",
                "testCreator",
                "testTerminator",
                "PUBLISH",
                LocalDateTime.now(),
                LocalDateTime.now());

        return Response.success(WorkOrderResponseDto.from(List.of(dto, dto)));
    }

    @GetMapping("/grouping")
    Response<WorkOrderGroupingResponseDto> grouping(){
        WorkOrderSummary publishDto = new WorkOrderSummary(
                0L,
                "testModel",
                "testSerialNo",
                "testCreator",
                "testTerminator",
                "PUBLISH",
                LocalDateTime.now(),
                LocalDateTime.now());
        WorkOrderSummary progressDto = new WorkOrderSummary(
                0L,
                "testModel",
                "testSerialNo",
                "testCreator",
                "testTerminator",
                "PROGRESS",
                LocalDateTime.now(),
                LocalDateTime.now());
        WorkOrderSummary finishDto = new WorkOrderSummary(
                0L,
                "testModel",
                "testSerialNo",
                "testCreator",
                "testTerminator",
                "FINISH",
                LocalDateTime.now(),
                LocalDateTime.now());

        return Response.success(WorkOrderGroupingResponseDto.from(
                List.of(publishDto, publishDto),
                List.of(progressDto),
                List.of(finishDto, finishDto, finishDto)
        ));
    }

    @GetMapping()
    Response<WorkOrderResponseDto> searchByPerson(@RequestParam(name = "employee-no") String employeeNo,
                                              @RequestParam(name = "name") String name) {
        WorkOrderSummary dto = new WorkOrderSummary(
                0L,
                "testModel",
                "testSerialNo",
                "testCreator",
                "testTerminator",
                "PROGRESS",
                LocalDateTime.now(),
                LocalDateTime.now());

        return Response.success(WorkOrderResponseDto.from(List.of(dto, dto)));
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

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody WorkOrderUpdateStatusRequestDto dto){
        return Response.success();
    }
}
