package com.sdi.common.api.service;

import com.sdi.common.api.dto.request.DisconnectRequestDto;
import com.sdi.common.api.dto.MemberInfoDto;
import com.sdi.common.api.dto.request.MessageRequestDto;
import com.sdi.common.api.dto.response.MessageResponseDto;
import com.sdi.common.api.entity.MemberEntity;
import com.sdi.common.api.entity.NotificationEntity;
import com.sdi.common.api.repository.EmitterRepository;
import com.sdi.common.api.repository.MemberRepository;
import com.sdi.common.api.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class SseService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // SSE 연결 지속 시간 : 1시간

    private final EmitterRepository emitterRepository;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(MemberInfoDto memberInfo, String lastEventId) {
        String emitterId = makeTimeIncludeId(memberInfo);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitter.onError((e) -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(memberInfo);
        sendInitMessage(emitter, eventId, emitterId, createInitMessage(emitterId, eventId, memberInfo));

        // Header의 LastEventId가 있을 때 이벤트 재전송하는 로직
//        if (hasLostData(lastEventId)) {
//            sendLostData(lastEventId, memberInfo.roleType(), emitterId, emitter);
//        }

        return emitter;
    }

    private void sendInitMessage(SseEmitter emitter, String eventId, String emitterId, String data) {
        try {
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
                "[ roleType : " + memberInfo.roleType() + ", employeeNo : " + memberInfo.employeeNo() + " ]";
    }

    public void sendToReceiver(MessageRequestDto messageRequestDto) {
        Map<String, SseEmitter> receivers = emitterRepository.findAllEmitterStartWithRoleType(messageRequestDto.receiverGroup());
        for (String key : receivers.keySet()) {
            SseEmitter emitter = receivers.get(key);
            String eventId = makeTimeIncludeId(messageRequestDto.receiverGroup());
            send(emitter, eventId, key, messageRequestDto);
        }
    }

    public void disconnect(DisconnectRequestDto disconnectRequestDto) {
        emitterRepository.deleteAllEmitterStartWithId(makeRoleTypeEmployeeNo(disconnectRequestDto));
    }

    // Header의 LastEventId가 있을 때 이벤트 재전송하는 로직
    /*
        private void sendLostData(String lastEventId, RoleType roleType, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartsWithMemberId(roleType.getCode());
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> send(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }
     */

    private NotificationEntity saveToNotificationDB(MessageRequestDto messageRequestDto) {
        MemberEntity sender = memberRepository.findByEmployeeNo(messageRequestDto.senderId())
                .orElseThrow(() -> new IllegalArgumentException("발신자 아이디를 찾을 수 없습니다."));
        MemberEntity receiver = memberRepository.findByEmployeeNo(messageRequestDto.receiverId())
                .orElseThrow(() -> new IllegalArgumentException("수신자 아이디를 찾을 수 없습니다."));
        NotificationEntity savedNotification = NotificationEntity.of(sender, receiver, messageRequestDto);
        notificationRepository.save(savedNotification);
        return savedNotification;
    }

    private void send(SseEmitter emitter, String eventId, String emitterId, MessageRequestDto messageRequestDto) {
        try {
            NotificationEntity savedNotification = saveToNotificationDB(messageRequestDto);
            Object data = MessageResponseDto.of(messageRequestDto, savedNotification.getId());
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private String makeTimeIncludeId(MemberInfoDto memberInfo) {
        return makeRoleTypeEmployeeNo(memberInfo) + "_" + System.currentTimeMillis();
    }

    private String makeTimeIncludeId(String roleType) {
        return roleType + "_" + System.currentTimeMillis();
    }

    private static String makeRoleTypeEmployeeNo(MemberInfoDto memberInfo) {
        return memberInfo.roleType() + "_" + memberInfo.employeeNo();
    }

    private static String makeRoleTypeEmployeeNo(DisconnectRequestDto disconnectRequestDto) {
        return disconnectRequestDto.role() + "_" + disconnectRequestDto.employeeNo();
    }
}
