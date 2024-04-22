package com.sdi.apiserver.api.request_response;

import com.sdi.apiserver.api.request_response.dto.requset.JigRepairRequestDto;
import com.sdi.apiserver.api.request_response.dto.requset.JigRequestRequestDto;
import com.sdi.apiserver.api.request_response.response.RequestJigAllResponseDto;
import com.sdi.apiserver.api.request_response.response.RequestJigAllResponseDto.JigRequest;
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
    Response<RequestJigAllResponseDto> all(){
        RequestJigAllResponseDto dto = new RequestJigAllResponseDto(
                List.of(
                        new JigRequest("testFrom", "testTo", "testModel", 10, LocalDateTime.now()),
                        new JigRequest("testFrom2", "testTo2", "testModel2", 20, LocalDateTime.now())
                )
        );

        return Response.success(dto);
    }
}
