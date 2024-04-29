package com.sdi.apiserver.api.request_response;

import com.sdi.apiserver.api.request_response.dto.request.RequestJigResponseRequestDto;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/response")
public class ResponseController {

    @PostMapping("/jig")
    Response<Void> jig(@RequestBody RequestJigResponseRequestDto dto){
        return Response.success();
    }
}
