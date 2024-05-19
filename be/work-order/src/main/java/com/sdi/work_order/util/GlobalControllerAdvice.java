package com.sdi.work_order.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(CommonException.class)
    ResponseEntity<?> handler(CommonException e) {
        log.error("Error occur {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity handler(FeignException e) throws JsonProcessingException {
        log.error("Error occur {}", e.getMessage());
        String responseJson = e.contentUTF8();
        Map<String, String> responseMap = new ObjectMapper().readValue(responseJson, Map.class);

        return ResponseEntity
                .status(e.status())
                .body(responseMap);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<?> handler(RuntimeException e) {
        log.error("Error occur {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
