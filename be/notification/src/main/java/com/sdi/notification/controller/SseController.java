package com.sdi.notification.controller;

import com.sdi.notification.dto.MemberInfoDto;
import com.sdi.notification.dto.request.MessageRequestDto;
import com.sdi.notification.service.ApiService;
import com.sdi.notification.service.SseService;
import com.sdi.notification.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/v1/notification/sse")
@RequiredArgsConstructor
class SseController {
    private final SseService sseService;
    private final ApiService apiService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    SseEmitter subscribe(@RequestHeader("Authorization") String accessToken) {
        MemberInfoDto currentMember = apiService.getMember(accessToken);

        return sseService.subscribe(currentMember, ""); // EventSource에 헤더를 달아서 전송받지 못한 이벤트를 트래킹 가능한데 이건 프론트에서 뭔가 뚝딱해야됨
//        return sseService.subscribe(employeeNo, lastEventId);
    }

    @PostMapping(value = "/send-message")
    Response<Void> sendMessage(@RequestBody MessageRequestDto messageRequestDto) {
        sseService.sendToReceiver(messageRequestDto);
        return Response.success();
    }

    @DeleteMapping("/disconnect")
    Response<Void> disconnect(@RequestHeader("Authorization") String accessToken) {
        MemberInfoDto currentMember = apiService.getMember(accessToken);
        sseService.disconnect(currentMember);
        return Response.success();
    }
}