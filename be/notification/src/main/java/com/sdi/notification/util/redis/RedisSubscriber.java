package com.sdi.notification.util.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.notification.dto.request.MessageRequestDto;
import com.sdi.notification.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final SseEmitterService sseEmitterService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String channel = Arrays.toString(message.getChannel());

            MessageRequestDto messageRequestDto = objectMapper.readValue(message.getBody(),
                    MessageRequestDto.class);

            // 클라이언트에게 event 데이터 전송
            sseEmitterService.sendToClient(channel, messageRequestDto);
        } catch (IOException e) {
            log.error("IOException is occurred. ", e);
        }
    }
}