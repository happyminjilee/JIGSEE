package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.jig.dto.request.JigCreateRequestDto;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jig")
public class JigController {

    @PostMapping()
    Response<Void> add(){
        return Response.success();
    }
}
