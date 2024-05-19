package com.sdi.watching.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
class HealthController {

    @GetMapping("/health")
    Map<String, String> health() {
        Map<String, String> map = new HashMap<>();
        map.put("resultCode", "SUCCESS");
        map.put("result", null);
        return map;
    }
}
