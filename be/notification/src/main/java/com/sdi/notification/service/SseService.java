package com.sdi.notification.service;

import com.sdi.notification.dto.MemberInfoDto;
import com.sdi.notification.dto.request.MessageRequestDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface SseService {
    SseEmitter subscribe(MemberInfoDto memberInfo);

    void sendNotification(MessageRequestDto messageRequestDto);

    void disconnect(MemberInfoDto memberResponseDto);

    void disconnectAll();

    List<String> searchAllConnection();
}
