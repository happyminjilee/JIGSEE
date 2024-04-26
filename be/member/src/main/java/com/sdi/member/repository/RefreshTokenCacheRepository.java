package com.sdi.member.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RefreshTokenCacheRepository {

    private final RedisTemplate<String, String> refreshTokenRedisTemplate;

    public void setRefreshToken(String employeeNo, String refreshToken, long refreshTokenExpiry) {
        String key = getKey(employeeNo);
        long refreshTokenExpiryInSeconds = TimeUnit.MILLISECONDS.toSeconds(refreshTokenExpiry);
        refreshTokenRedisTemplate.opsForValue().set(key, refreshToken, refreshTokenExpiryInSeconds, TimeUnit.SECONDS);
        log.info("set refreshToken : {}", employeeNo);
    }

    public String getRefreshToken(String employeeNo) {
        String key = getKey(employeeNo);
        return refreshTokenRedisTemplate.opsForValue().get(key);
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
