package com.sdi.member.filter;

import com.sdi.member.jwt.AuthToken;
import com.sdi.member.jwt.AuthTokenProvider;
import com.sdi.member.util.HeaderUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/v1/login") || request.getRequestURI().equals("/v1/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessTokenStr = HeaderUtils.getAccessToken(request);
        AuthToken accessToken = tokenProvider.convertAuthAccessToken(accessTokenStr);

        // 토큰이 입증됐다면,
        if (accessToken.validateOrException()) {
            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}