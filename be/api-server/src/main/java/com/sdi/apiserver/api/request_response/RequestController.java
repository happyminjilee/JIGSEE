package com.sdi.apiserver.api.request_response;

import com.sdi.apiserver.api.request_response.dto.requset.JigRepairRequestDto;
import com.sdi.apiserver.api.request_response.dto.requset.JigRequestRequestDto;
import com.sdi.apiserver.api.request_response.response.RequestJigListResponseDto;
import com.sdi.apiserver.api.request_response.response.RequestJigListResponseDto.JigRequest;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/request")
public class RequestController {

    @PostMapping("/jig")
    Response<Void> jig(@RequestBody JigRequestRequestDto dto){
        return Response.success();
    }

    @PostMapping("/repair")
    Response<Void> repair(@RequestBody JigRepairRequestDto dto){
        return Response.success();
    }

    @GetMapping("/jig")
    Response<RequestJigListResponseDto> all(){
        RequestJigListResponseDto dto = new RequestJigListResponseDto(
                true,
                1,
                1,
                List.of(
                        new JigRequest(0L, "testFrom", "testTo", "testModel", 10, LocalDateTime.now()),
                        new JigRequest(1L, "testFrom2", "testTo2", "testModel2", 20, LocalDateTime.now())
                )
        );

        return Response.success(dto);
    }
}
