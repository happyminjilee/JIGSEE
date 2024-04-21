package com.sdi.common.filter;

import com.sdi.common.jwt.AuthToken;
import com.sdi.common.jwt.AuthTokenProvider;
import com.sdi.common.util.HeaderUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenStr = HeaderUtils.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        // 토큰이 입증됐다면,
        if (token.validate()) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            // jwt access token에 문제있다면 401에러 리턴
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }
}