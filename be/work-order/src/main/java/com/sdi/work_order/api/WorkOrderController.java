package com.sdi.work_order.api;

import com.sdi.work_order.dto.reponse.WorkOrderDetailResponseDto;
import com.sdi.work_order.dto.reponse.WorkOrderGroupingResponseDto;
import com.sdi.work_order.dto.reponse.WorkOrderResponseDto;
import com.sdi.work_order.dto.request.WorkOrderCreateRequestDto;
import com.sdi.work_order.dto.request.WorkOrderSaveRequestDto;
import com.sdi.work_order.dto.request.WorkOrderUpdateStatusRequestDto;
import com.sdi.work_order.application.WorkOrderService;
import com.sdi.work_order.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/work-order")
@RequiredArgsConstructor
class WorkOrderController {

    private final WorkOrderService workOrderService;

    @GetMapping("/detail")
    Response<WorkOrderDetailResponseDto> detail(@RequestParam(name = "serial-no") String serialNo){
        return Response.success(null);
    }

    @GetMapping("/all")
    Response<WorkOrderResponseDto> all(@RequestParam(name = "status") String status){
        return Response.success(null);
    }

    @GetMapping("/grouping")
    Response<WorkOrderGroupingResponseDto> grouping(){
        WorkOrderGroupingResponseDto dto = workOrderService.grouping();
        return Response.success(dto);
    }

    @GetMapping
    Response<WorkOrderResponseDto> findByPerson(@RequestParam(name = "employee-no") String employeeNo,
                                          @RequestParam(name = "name") String name){
        return Response.success(null);
    }

    @PostMapping
    Response<Void> create(@RequestBody WorkOrderCreateRequestDto dto){
        workOrderService.create(dto);
        return Response.success();
    }

    @PutMapping("/tmp")
    Response<Void> tmpSave(@RequestBody WorkOrderSaveRequestDto dto){
        workOrderService.tmpSave(dto.id(), dto.checkList());
        return Response.success();
    }

    @PutMapping("/done")
    Response<Void> done(@RequestBody WorkOrderSaveRequestDto dto){
        workOrderService.save(dto.id(), dto.checkList());
        return Response.success();
    }

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody WorkOrderUpdateStatusRequestDto dto){
        workOrderService.updateStatus(dto.list());
        return Response.success();
    }
}
