package com.sdi.jig.api;

import com.sdi.jig.util.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/health")
class HealthController {

    @GetMapping
    Response<Void> health(){
        return Response.success();
    }
}
