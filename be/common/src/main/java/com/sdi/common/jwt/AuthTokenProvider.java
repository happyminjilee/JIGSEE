package com.sdi.common.jwt;

import com.sdi.common.dto.MemberPrincipal;
import com.sdi.common.util.CommonException;
import com.sdi.common.util.ErrorCode;
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
    private final String key;
    private static final String AUTHORITIES_KEY = "role";

    /**
     * 액세스 토큰 생성
     */
    public AuthToken createAuthToken(String id, String role, long expiry) {
        return new AuthToken(id, role, key, expiry);
    }

    /**
     * 리프레시 토큰 생성 - MemberService에서 로그인과 리프레시 토큰 생성시 사용
     */
    public AuthToken createAuthToken(String id, long expiry) {
        return new AuthToken(id, key, expiry);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
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
            MemberPrincipal principal = MemberPrincipal.of(authToken.getMemberEmployeeNo(), null, authorities);
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new CommonException(ErrorCode.INVALID_TOKEN, "Token is invalid");
        }
    }

}
