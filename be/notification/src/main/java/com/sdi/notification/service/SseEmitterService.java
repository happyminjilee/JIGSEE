package com.sdi.notification.service;

import com.sdi.notification.dto.MemberInfoDto;
import com.sdi.notification.dto.request.MessageRequestDto;
import com.sdi.notification.dto.response.MessageResponseDto;
import com.sdi.notification.entity.NotificationEntity;
import com.sdi.notification.repository.EmitterRepository;
import com.sdi.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SseEmitterService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // SSE 연결 지속 시간 : 1시간
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    public SseEmitter createEmitter(String emitterId) {
        return emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
    }

    public void deleteEmitter(String emitterKey) {
        emitterRepository.deleteById(emitterKey);
    }

    public void sendInitMessage(SseEmitter emitter, String emitterId, MemberInfoDto memberInfo) {
        try {
            String eventId = makeTimeIncludeId(memberInfo);
            String data = createInitMessage(emitterId, eventId, memberInfo);
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private String createInitMessage(String emitterId, String eventId, MemberInfoDto memberInfo) {
        return "SSE Connected : \n" +
                "[ emitterId : " + emitterId + " ]\n" +
                "[ eventId : " + eventId + " ]\n" +
                "[ role : " + memberInfo.role() + ", employeeNo : " + memberInfo.employeeNo() + " ]";
    }

    public void sendToClient(String role, MessageRequestDto messageRequestDto) {
        Map<String, SseEmitter> receivers = emitterRepository.findAllEmitterStartWithRoleType(role);
        for (String key : receivers.keySet()) {
            SseEmitter emitter = receivers.get(key);
            String receiverId = key.split("_")[1];
            String eventId = makeTimeIncludeId(role, receiverId);
            send(emitter, eventId, key, messageRequestDto, receiverId);
        }
    }
//    public void sendNotificationToClient(String emitterKey, NotificationDto notificationDto) {
//        emitterRepository.findById(emitterKey)
//                .ifPresent(emitter -> send(notificationDto, emitterKey, emitter));
//    }

    private void send(SseEmitter emitter, String eventId, String emitterId, MessageRequestDto messageRequestDto, String receiverId) {
        try {
            NotificationEntity savedNotification = saveToNotificationDB(messageRequestDto, receiverId);
            Object data = MessageResponseDto.of(messageRequestDto, savedNotification.getId(), receiverId);
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException | IllegalStateException e) {
            emitterRepository.deleteById(emitterId);
        }
    }

    public void disconnect(MemberInfoDto memberInfo) {
        emitterRepository.deleteAllEmitterStartWithId(makeRoleTypeEmployeeNo(memberInfo));
    }

    public void disconnectAll() {
        emitterRepository.deleteAllEmitter();
    }

    public List<String> searchAllConnection() {
        return emitterRepository.findAllEmitter();
    }

    private NotificationEntity saveToNotificationDB(MessageRequestDto messageRequestDto, String receiverId) {
        NotificationEntity savedNotification = NotificationEntity.of(messageRequestDto.senderId(), receiverId, messageRequestDto);
        notificationRepository.save(savedNotification);
        return savedNotification;
    }

    private String makeTimeIncludeId(MemberInfoDto memberInfo) {
        return makeRoleTypeEmployeeNo(memberInfo) + "_" + System.currentTimeMillis();
    }

    private String makeTimeIncludeId(String roleType, String employeeNo) {
        return roleType + "_" + employeeNo + "_" + System.currentTimeMillis();
    }

    private static String makeRoleTypeEmployeeNo(MemberInfoDto memberInfo) {
        return memberInfo.role() + "_" + memberInfo.employeeNo();
    }

}
