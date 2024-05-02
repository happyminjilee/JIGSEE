package com.sdi.apiserver.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

/**
 * HTTP 요청 헤더에서 JWT 토큰을 추출
 */
public class HeaderUtils {

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerValue == null) {
            return null;
        }

        return headerValue;
    }

    public static String getRefreshToken(HttpServletRequest request) {
        String headerValue = request.getHeader("RefreshToken");
        if (headerValue == null) {
            return null;
        }

        return headerValue;
    }

    public static void addAccessToken(HttpServletResponse response, String value) {
        String headerValue = HttpHeaders.AUTHORIZATION;
        response.addHeader(headerValue, value);
    }

    public static void addRefreshToken(HttpServletResponse response, String value) {
        String headerValue = "RefreshToken";
        response.addHeader(headerValue, value);
    }
}
