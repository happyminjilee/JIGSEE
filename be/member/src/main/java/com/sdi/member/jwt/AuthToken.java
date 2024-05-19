package com.sdi.member.jwt;

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
public class AuthToken {

    @Getter
    private final String token;
    private final String key;

    private static final String AUTHORITIES_KEY = "role";

    /**
     * 리프레시 토큰 생성
     */
    AuthToken(String id, String key, long expiry) {
        this.token = generateToken(id, key, expiry);
        this.key = key;
    }

    /**
     * 액세스 토큰 생성
     */
    AuthToken(String id , String role, String key, long expiry) {
        this.token = generateToken(id, role, key, expiry);
        this.key = key;
    }

    /**
     * 토큰에서 employeeNo 추출
     */
    public String getMemberEmployeeNo() {
        return Objects.requireNonNull(extractClaims()).get("employeeNo", String.class);
    }

    public boolean validate() {
        return extractClaims() != null;
    }

    public boolean validateOrException() {
         return Jwts.parserBuilder()
                 .setSigningKey(getKey(key))
                 .build()
                 .parseClaimsJws(token)
                 .getBody() != null;
    }

    /**
     * 토큰을 파싱하여 Claim을 추출
     */
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

    /**
     * 실제 리프레시 토큰을 생성하는 코드
     */
    public String generateToken(String id, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("employeeNo", id);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 실제 액세스 토큰을 생성하는 코드
     */
    public String generateToken(String id, String role, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("employeeNo", id);
        claims.put(AUTHORITIES_KEY, role);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰의 기간이 지났다면 토큰 반환,
     * 유효하다면 null 반환
     */
    public Claims getExpiredClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey(key))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
            return e.getClaims();
        }
        return null;
    }

    private Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
