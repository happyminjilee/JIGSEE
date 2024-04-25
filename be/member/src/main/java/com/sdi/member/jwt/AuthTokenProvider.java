package com.sdi.member.jwt;

import com.sdi.member.dto.MemberPrincipalDto;
import com.sdi.member.util.CommonException;
import com.sdi.member.util.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class AuthTokenProvider {
    private final String accessKey;
    private final String refreshKey;
    private static final String AUTHORITIES_KEY = "role";

    /**
     * 액세스 토큰 생성
     */
    public AuthToken createAuthToken(String id, String role, long expiry) {
        return new AuthToken(id, role, accessKey, expiry);
    }

    /**
     * 리프레시 토큰 생성 - MemberService에서 로그인과 리프레시 토큰 생성시 사용
     */
    public AuthToken createAuthToken(String id, long expiry) {
        return new AuthToken(id, refreshKey, expiry);
    }

    public AuthToken convertAuthAccessToken(String token) {
        return new AuthToken(token, accessKey);
    }

    public AuthToken convertAuthRefreshToken(String token) {
        return new AuthToken(token, refreshKey);
    }

    /**
     * 토큰을 통한 UsernamePasswordAuthenticationToken 발급
     */
    public Authentication getAuthentication(AuthToken authToken) {
        if (authToken.validate()) {
            Claims claims = authToken.extractClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{
                                    claims.get(AUTHORITIES_KEY).toString()
                            })
                            .map(SimpleGrantedAuthority::new)
                            .toList();
            MemberPrincipalDto principal = MemberPrincipalDto.of(authToken.getMemberEmployeeNo(), null, authorities);
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new CommonException(ErrorCode.INVALID_TOKEN, "Token is invalid");
        }
    }

}
