package com.sdi.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Getter
    private final String token;
    private final String key;

    // JWT Token 발급
    public String createToken(String name, String employeeNo, String role, String key, long expireTimeMs) {
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 넣어 줌으로써 나중에 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("name", name);
        claims.put("employeeNo", employeeNo);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    // Claims에서 name 꺼내기
    public String getName() {
        return Objects.requireNonNull(extractClaims()).get("name", String.class);
    }

    // 발급된 Token이 만료 시간이 지났는지 체크
    public Claims getExpiredClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey(key))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 경우, Claim 가져옴
            log.info("Expired JWT token");
            return e.getClaims();
        }
        return null;
    }

    // SecretKey를 사용해 Token Parsing
    public Claims extractClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey(key))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    private Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}