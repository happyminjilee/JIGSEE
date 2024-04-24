package com.sdi.common.api.service;

import com.sdi.common.api.repository.EmitterRepository;
import com.sdi.common.api.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class SseService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // SSE 연결 지속 시간 : 1시간

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(String employeeNo, String lastEventId) {
        String emitterId = makeTimeIncludeId(employeeNo);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitter.onError((e) -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(employeeNo);
        send(emitter, eventId, emitterId, createInitMessage(employeeNo));

        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, employeeNo, emitterId, emitter);
        }

        return emitter;
    }

    public void disconnect(String employeeNo) {
        emitterRepository.deleteAllEmitterStartWithId(employeeNo);
    }

    private void sendLostData(String lastEventId, String employeeNo, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartsWithMemberId(employeeNo);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> send(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void send(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private String createInitMessage(String employeeNo) {
        return "SSE Connected : [ employeeNo : " + employeeNo + " ]";
    }

    private String makeTimeIncludeId(String employeeNo) {
        return employeeNo + "_" + System.currentTimeMillis();
    }

}
