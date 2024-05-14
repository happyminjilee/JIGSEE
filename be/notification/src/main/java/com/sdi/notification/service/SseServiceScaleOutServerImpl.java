package com.sdi.notification.service;

import com.sdi.notification.dto.MemberInfoDto;
import com.sdi.notification.dto.request.MessageRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
@Transactional
public class SseServiceScaleOutServerImpl implements SseService {
    private final SseEmitterService sseEmitterService;
    private final RedisMessageService redisMessageService;

    public SseEmitter subscribe(MemberInfoDto memberInfo) {
        String emitterId = makeTimeIncludeId(memberInfo);
        SseEmitter emitter = sseEmitterService.createEmitter(emitterId);

        sseEmitterService.sendInitMessage(emitter, emitterId, memberInfo);

        emitter.onTimeout(emitter::complete);
        emitter.onError((e) -> emitter.complete());
        emitter.onCompletion(() -> sseEmitterService.deleteEmitter(emitterId));

        return emitter;
    }

    /**
     * 클라이언트가 호출하는 알림 전송 요청 메소드
     * redis에 publish해야 함
     * @param messageRequestDto
     */
    public void sendNotification(MessageRequestDto messageRequestDto) {
        redisMessageService.publish(messageRequestDto);
    }

    public void disconnect(MemberInfoDto memberInfo) {
        sseEmitterService.disconnect(memberInfo);
    }

    public void disconnectAll() {
        sseEmitterService.disconnectAll();
    }

//    private NotificationEntity saveToNotificationDB(MessageRequestDto messageRequestDto, String receiverId) {
//        NotificationEntity savedNotification = NotificationEntity.of(messageRequestDto.senderId(), receiverId, messageRequestDto);
//        notificationRepository.save(savedNotification);
//        return savedNotification;
//    }

//    private void send(SseEmitter emitter, String eventId, String emitterId, MessageRequestDto messageRequestDto, String receiverId) {
//        try {
//            NotificationEntity savedNotification = saveToNotificationDB(messageRequestDto, receiverId);
//            Object data = MessageResponseDto.of(messageRequestDto, savedNotification.getId(), receiverId);
//            emitter.send(SseEmitter.event()
//                    .id(eventId)
//                    .data(data));
//        } catch (IOException e) {
//            emitterRepository.deleteById(emitterId);
//        }
//    }

    private String makeTimeIncludeId(MemberInfoDto memberInfo) {
        return makeRoleTypeEmployeeNo(memberInfo) + "_" + System.currentTimeMillis();
    }

    private String makeTimeIncludeId(String roleType, String employeeNo) {
        return roleType + "_" + employeeNo + "_" + System.currentTimeMillis();
    }

    private static String makeRoleTypeEmployeeNo(MemberInfoDto memberInfo) {
        return memberInfo.role() + "_" + memberInfo.employeeNo();
    }


    public List<String> searchAllConnection() {
        return sseEmitterService.searchAllConnection();
    }
}
