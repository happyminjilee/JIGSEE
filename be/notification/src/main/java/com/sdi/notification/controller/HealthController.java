package com.sdi.notification.controller;

import com.sdi.notification.util.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HealthController {

    @GetMapping("/health")
    Response<Void> health(){
        return Response.success();
    }
}
