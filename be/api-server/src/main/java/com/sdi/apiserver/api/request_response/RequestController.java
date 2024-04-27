package com.sdi.apiserver.api.request_response;

import com.sdi.apiserver.api.request_response.dto.request.RepairJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.request.RequestJigRequestDto;
import com.sdi.apiserver.api.request_response.dto.response.RepairJigDetailResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RepairJigDetailResponseDto.RepairJigDetailJigitem;
import com.sdi.apiserver.api.request_response.dto.response.RepairJigListResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RepairJigListResponseDto.RequestJigRepair;
import com.sdi.apiserver.api.request_response.dto.response.RequestJigDetailResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RequestJigListResponseDto;
import com.sdi.apiserver.api.request_response.dto.response.RequestJigListResponseDto.JigRequest;
import com.sdi.apiserver.util.RequestJigCount;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/request")
public class RequestController {

    @PostMapping("/jig")
    Response<Void> jig(@RequestBody RequestJigRequestDto dto) {
        return Response.success();
    }

    @PostMapping("/repair")
    Response<Void> repair(@RequestBody RepairJigRequestDto dto) {
        return Response.success();
    }

    @GetMapping("/jig/all")
    Response<RequestJigListResponseDto> all(@RequestParam(name = "filter") String filter) {
        RequestJigListResponseDto dto = new RequestJigListResponseDto(
                true,
                1,
                1,
                List.of(
                        new JigRequest(0L, "testStatus", "testFrom", "testTo", LocalDateTime.now(), LocalDateTime.now()),
                        new JigRequest(1L, "testStatus2", "testFrom2", "testTo2", LocalDateTime.now(), LocalDateTime.now())
                )
        );

        return Response.success(dto);
    }

    @GetMapping("/jig/detail")
    Response<RequestJigDetailResponseDto> detailRequestJig(@RequestParam(name = "request-jig-id") Long id) {
        RequestJigDetailResponseDto dto = RequestJigDetailResponseDto.from(
                false,
                1L,
                "FINISH",
                "from1",
                "to1",
                "memo1",
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(
                        RequestJigCount.from("model", 1),
                        RequestJigCount.from("model2", 2)
                )
        );
        return Response.success(dto);
    }

    @GetMapping("/repair")
    Response<RepairJigListResponseDto> repairRequestJig(){
        RepairJigListResponseDto dto = RepairJigListResponseDto.from(
                1,
                3,
                List.of(
                        RequestJigRepair.from(1L, "from1", LocalDateTime.now()),
                        RequestJigRepair.from(2L, "from2", LocalDateTime.now())
                )
        );
        return Response.success(dto);
    }

    @GetMapping("/repair/detail")
    Response<RepairJigDetailResponseDto> detailRepairRequestJig(@RequestParam(name = "repair-jig-id") Long id){
        RepairJigDetailResponseDto dto = RepairJigDetailResponseDto.from(
                1L,
                "from1",
                LocalDateTime.now(),
                List.of(
                        new RepairJigDetailJigitem("m1", "s1"),
                        new RepairJigDetailJigitem("m1", "s2")
                )
        );
        return Response.success(dto);
    }
}
