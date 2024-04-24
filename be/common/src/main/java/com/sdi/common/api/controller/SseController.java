package com.sdi.common.api.controller;

import com.sdi.common.api.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {
    private final SseService sseService;

    @GetMapping(value = "/subscribe/{employeeNo}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable("employeeNo") String employeeNo) {
        return sseService.subscribe(employeeNo, ""); // EventSource에 헤더를 달아서 전송받지 못한 이벤트를 트래킹 가능한데 이건 프론트에서 뭔가 뚝딱해야됨
//        return sseService.subscribe(employeeNo, lastEventId);
    }
}
