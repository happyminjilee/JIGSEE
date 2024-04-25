package com.sdi.common.api.controller;

import com.sdi.common.api.dto.request.DisconnectRequestDto;
import com.sdi.common.api.dto.MemberInfoDto;
import com.sdi.common.api.dto.request.MessageRequestDto;
import com.sdi.common.api.service.SseService;
import com.sdi.common.util.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
class SseController {
    private final SseService sseService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    SseEmitter subscribe(@RequestParam("employee-no") String employeeNo, @RequestParam("role-type") String roleType) {
        MemberInfoDto memberInfo = new MemberInfoDto(employeeNo, RoleType.of(roleType));
        return sseService.subscribe(memberInfo, ""); // EventSource에 헤더를 달아서 전송받지 못한 이벤트를 트래킹 가능한데 이건 프론트에서 뭔가 뚝딱해야됨
//        return sseService.subscribe(employeeNo, lastEventId);
    }

    @PostMapping(value = "/send-message")
    void sendMessage(@RequestBody MessageRequestDto messageRequestDto) {
        sseService.sendToReceiver(messageRequestDto);
    }

    @DeleteMapping("/disconnect")
    void disconnect(@RequestBody DisconnectRequestDto disconnectRequestDto) {
        sseService.disconnect(disconnectRequestDto);
    }

}
