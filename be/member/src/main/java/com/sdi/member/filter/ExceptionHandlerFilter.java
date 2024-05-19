package com.sdi.member.filter;

import com.google.gson.Gson;
import com.sdi.member.util.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    /**
     * 토큰 관련 에러 핸들링
     * JwtTokenFilter 에서 발생하는 에러를 핸들링해준다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            //토큰의 유효기간 만료
            log.error("만료된 토큰입니다");
            setErrorResponse(response, ErrorCode.EXPIRED_ACCESS_TOKEN);

        } catch (JwtException | IllegalArgumentException e) {
            //유효하지 않은 토큰
            log.error("유효하지 않은 토큰이 입력되었습니다.");
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);

        } catch (NoSuchElementException e) {
            //사용자 찾을 수 없음
            log.error("사용자를 찾을 수 없습니다.");
            setErrorResponse(response, ErrorCode.USER_NOT_FOUND);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> responseBody = new LinkedHashMap<>();
        result.put("message", errorCode.getMessage());

        switch (errorCode) {
            case EXPIRED_ACCESS_TOKEN:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseBody.put("resultCode", "EXPIRED_ACCESS_TOKEN");
                break;
            case USER_NOT_FOUND:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseBody.put("resultCode", "USER_NOT_FOUND");
                break;
            default:
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                responseBody.put("resultCode", "INVALID_ACCESS_TOKEN");
        }

        responseBody.put("result", result);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseBody);

        response.getWriter().print(jsonResponse);
    }
}