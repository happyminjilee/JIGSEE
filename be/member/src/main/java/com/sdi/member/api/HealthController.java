package com.sdi.member.api;

import com.sdi.member.util.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
class HealthController {

    @GetMapping("/health")
    Response<Void> health(){
        return Response.success();
    }
}
