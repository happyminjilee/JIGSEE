package com.sdi.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

/**
 * HTTP 요청 헤더에서 JWT 토큰을 추출
 */
public class HeaderUtils {
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerValue == null) {
            return null;
        }
        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
