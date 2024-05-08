package com.sdi.work_order.api;

import com.sdi.work_order.dto.reponse.WorkOrderDetailResponseDto;
import com.sdi.work_order.dto.reponse.WorkOrderDoneResponseDto;
import com.sdi.work_order.dto.reponse.WorkOrderGroupingResponseDto;
import com.sdi.work_order.dto.reponse.WorkOrderResponseDto;
import com.sdi.work_order.dto.request.WorkOrderAutoCreateRequestDto;
import com.sdi.work_order.dto.request.WorkOrderCreateRequestDto;
import com.sdi.work_order.dto.request.WorkOrderSaveRequestDto;
import com.sdi.work_order.dto.request.WorkOrderUpdateStatusRequestDto;
import com.sdi.work_order.application.WorkOrderService;
import com.sdi.work_order.util.Response;
import com.sdi.work_order.util.WorkOrderStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/work-order")
@RequiredArgsConstructor
class WorkOrderController {

    private static final String ACCESS_TOKEN_PREFIX = "Authorization";

    private final WorkOrderService workOrderService;

    @GetMapping("/detail")
    Response<WorkOrderDetailResponseDto> detail(@RequestParam(name = "work-order-id") Long workOrderId, HttpServletRequest request) {
        log.info("\'{}\' 상세 조회 요청", workOrderId);
        WorkOrderDetailResponseDto dto = workOrderService.detail(getAccessToken(request), workOrderId);
        return Response.success(dto);
    }

    @GetMapping("/all")
    Response<WorkOrderResponseDto> all(@RequestParam(name = "status", required = false) WorkOrderStatus status,
                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size,
                                       HttpServletRequest request) {
        log.info("\'{}\' 페이지 조회 요청 (크기 \'{}\')", page, size);
        WorkOrderResponseDto all = workOrderService.all(getAccessToken(request), status, page, size);
        return Response.success(all);
    }

    @GetMapping("/grouping")
    Response<WorkOrderGroupingResponseDto> grouping(HttpServletRequest request) {
        log.info("그룹핑 조회 요청");
        WorkOrderGroupingResponseDto dto = workOrderService.grouping(getAccessToken(request));
        return Response.success(dto);
    }

    @GetMapping
    Response<WorkOrderResponseDto> findByPerson(@RequestParam(name = "employee-no", required = false) String employeeNo,
                                                @RequestParam(name = "name", required = false) String name,
                                                @RequestParam(name = "page", defaultValue = "1") int page,
                                                @RequestParam(name = "size", defaultValue = "10") int size,
                                                HttpServletRequest request) {
        log.info("[employeeNo: \'{}\' | name: \'{}\']가 작성한 {} 페이지 조회 요청 (크기 \'{}\')", employeeNo, name, page, size);
        WorkOrderResponseDto byPerson = workOrderService.findByPerson(getAccessToken(request), employeeNo, name, page, size);
        return Response.success(byPerson);
    }

    @PostMapping
    Response<Void> create(@RequestBody WorkOrderCreateRequestDto dto, HttpServletRequest request) {
        log.info("JigItem : \'{}\' Work Order 생성 요청", dto.serialNo());
        workOrderService.create(getAccessToken(request), dto);
        return Response.success();
    }

    @PutMapping("/tmp")
    Response<Void> tmpSave(@RequestBody WorkOrderSaveRequestDto dto) {
        log.info("Work Order Id : \'{}\' 임시 저장 요청", dto.id());
        workOrderService.tmpSave(dto.id(), dto.checkList());
        return Response.success();
    }

    @PutMapping("/done")
    Response<WorkOrderDoneResponseDto> save(@RequestBody WorkOrderSaveRequestDto dto, HttpServletRequest request) {
        log.info("Work Order Id : \'{}\' 저장 요청", dto.id());
        WorkOrderDoneResponseDto save = workOrderService.save(getAccessToken(request), dto.id(), dto.checkList());
        return Response.success(save);
    }

    @PutMapping("/status")
    Response<Void> updateStatus(@RequestBody WorkOrderUpdateStatusRequestDto dto) {
        log.info("Work Order : [\'{}\'] 상태 변경 요청", dto);
        workOrderService.updateStatus(dto.list());
        return Response.success();
    }

    @PostMapping("/auto")
    Response<Void> autoCreate(@RequestBody WorkOrderAutoCreateRequestDto dto, HttpServletRequest request){
        log.info("\'{}\' 자동 저장 요청", dto);
        workOrderService.autoCreate(getAccessToken(request), dto);
        return Response.success();
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_PREFIX);
    }
}
