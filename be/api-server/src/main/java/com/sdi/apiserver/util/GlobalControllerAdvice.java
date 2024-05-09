package com.sdi.apiserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.apiserver.util.mattermost.NotificationManager;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Enumeration;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final NotificationManager notificationManager;

    @ExceptionHandler(CommonException.class)
    ResponseEntity<?> handler(CommonException e, HttpServletRequest request) {
        log.error("Error occur {}", e.getMessage());
        sendMatterMostMessage(e, request);
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<?> handler(RuntimeException e, HttpServletRequest request) {
        log.error("Error occur {}", e.getMessage());
        sendMatterMostMessage(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity handler(FeignException e, HttpServletRequest request) throws JsonProcessingException {
        log.error("Error occur {}", e.getMessage());
        String responseJson = e.contentUTF8();
        Map<String, String> responseMap = new ObjectMapper().readValue(responseJson, Map.class);

        sendMatterMostMessage(e, request);
        return ResponseEntity
                .status(e.status())
                .body(responseMap);
    }

    private void sendMatterMostMessage(Exception e, HttpServletRequest request) {
        notificationManager.sendNotification(e, request.getRequestURI(), getParams(request));
    }

    private String getParams(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.append("- ").append(key).append(" : ").append(request.getParameter(key)).append('\n');
        }

        return params.toString();
    }
}