package com.sdi.notification.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter); // Emitter 저장
    void saveEventCache(String eventCacheId, Object event); // 유실 방지로 이벤트 저장
    void deleteById(String id); // emitter id로 emitter 삭제
    void deleteAllEmitterStartWithId(String id); // 해당 회원과 관련된 모든 emitter 삭제
    void deleteAllEventCacheStartWithId(String id); // 해당 회원과 관련된 모든 이벤트 삭제
    Map<String, SseEmitter> findAllEmitterStartsWithMemberId(String memberId); // 해당 회원과 관련된 모든 emitter 조회
    Map<String, Object> findAllEventCacheStartsWithMemberId(String memberId); // 해당 회원과 관련된 모든 이벤트 조회
    Map<String, SseEmitter> findAllEmitterStartWithRoleType(String role); // 권한 별 조회
    void deleteAllEmitter(); // 현재 모든 connection 삭제

    List<String> findAllEmitter(); // 모든 connection 조회
}
