package com.sdi.notification.service;

import com.sdi.notification.dto.request.MessageRequestDto;
import com.sdi.notification.util.RoleType;
import com.sdi.notification.util.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisMessageService implements InitializingBean {

    private final RedisMessageListenerContainer container;
    private final RedisSubscriber subscriber; // 따로 구현한 Subscriber
    private final RedisTemplate<String, Object> redisTemplate;


    // bean 생성 후 수행되는 메소드, 모든 권한에 대해 구독
    @Override
    public void afterPropertiesSet() throws Exception {
        for (RoleType roleType : RoleType.values()) {
            container.addMessageListener(subscriber, new ChannelTopic(roleType.name()));
        }
    }
//
//    // 채널 구독
//    public void subscribe(String emitterId) {
//        container.addMessageListener(subscriber, ChannelTopic.of(emitterId));
//    }

    // 이벤트 발행
    public void publish(MessageRequestDto messageRequestDto) {
        redisTemplate.convertAndSend(messageRequestDto.receiverGroup(), messageRequestDto);
    }
//
//    // 구독 삭제
//    public void removeSubscribe(String emitterId) {
//        container.removeMessageListener(subscriber, ChannelTopic.of(emitterId));
//    }
}