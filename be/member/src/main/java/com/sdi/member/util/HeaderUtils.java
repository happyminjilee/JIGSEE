package com.sdi.member.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

/**
 * HTTP 요청 헤더에서 JWT 토큰을 추출
 */
public class HeaderUtils {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN = "RefreshToken";

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

    public static String getRefreshToken(HttpServletRequest request) {
        String headerValue = request.getHeader(REFRESH_TOKEN);
        if (headerValue == null) {
            return null;
        }
        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public static void addAccessToken(HttpServletResponse response, String value) {
        String headerValue = HttpHeaders.AUTHORIZATION;
        response.addHeader(headerValue, TOKEN_PREFIX + value);
    }

    public static void addRefreshToken(HttpServletResponse response, String value) {
        response.addHeader(REFRESH_TOKEN, TOKEN_PREFIX + value);
    }
}
