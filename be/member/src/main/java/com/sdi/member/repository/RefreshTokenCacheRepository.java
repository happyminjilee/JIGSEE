package com.sdi.member.repository;

import com.sdi.member.jwt.AuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RefreshTokenCacheRepository {

    private final RedisTemplate<String, AuthToken> refreshTokenRedisTemplate;
    private static final Duration MEMBER_CACHE_TTL = Duration.ofMillis(1800000); //30분

    public void setRefreshToken(String employeeNo, AuthToken refreshToken) {
        String key = getKey(employeeNo);
        refreshTokenRedisTemplate.opsForValue().set(key, refreshToken, MEMBER_CACHE_TTL);
        log.info("set refreshToken : {}, {}", key, refreshToken);
    }

    public AuthToken getRefreshToken(String employeeNo) {
        String key = getKey(employeeNo);
        AuthToken refreshToken = refreshTokenRedisTemplate.opsForValue().get(key);
        log.info("get refreshToken : {}, {}", key, refreshToken);
        return refreshToken;
    }

    public void updateRefreshToken(String employeeNo, AuthToken refreshToken) {
        String key = getKey(employeeNo);
        ValueOperations<String, AuthToken> valueOps = refreshTokenRedisTemplate.opsForValue();
        valueOps.set(key, refreshToken);
    }

    public void deleteRefreshToken(String employeeNo) {
        String key = getKey(employeeNo);
        refreshTokenRedisTemplate.delete(key);
    }

    public boolean existsRefreshToken(String employeeNo) {
        String key = getKey(employeeNo);
        boolean exists = refreshTokenRedisTemplate.hasKey(key);
        log.info("Refresh token exists for {}: {}", employeeNo, exists);
        return exists;
    }

    private String getKey(String employeeNo) {
        return "EmployeeNo:" + employeeNo;
    }
}
