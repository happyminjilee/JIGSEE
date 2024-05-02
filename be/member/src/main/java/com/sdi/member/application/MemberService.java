package com.sdi.member.application;

import com.sdi.member.dto.MemberDto;
import com.sdi.member.dto.response.MemberLoginResponseDto;
import com.sdi.member.dto.response.MemberResponseDto;
import com.sdi.member.entity.MemberEntity;
import com.sdi.member.jwt.AuthToken;
import com.sdi.member.jwt.AuthTokenProvider;
import com.sdi.member.repository.MemberRepository;
import com.sdi.member.repository.RefreshTokenCacheRepository;
import com.sdi.member.util.CommonException;
import com.sdi.member.util.ErrorCode;
import com.sdi.member.util.HeaderUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenCacheRepository refreshTokenCacheRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthTokenProvider tokenProvider;

    @Value("${jwt.access.expiry}")
    private long accessTokenExpiry;
    @Value("${jwt.refresh.expiry}")
    private long refreshTokenExpiry;

    /**
     * 가입 되어 있다면 멤버를 반환, 없다면 Exception 반환.
     */
    public MemberDto getMemberOrException(String employeeNo) {
        return memberRepository.findByEmployeeNo(employeeNo)
                .map(MemberDto::fromEntity)
                .orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 로그인
     * 1. 엑세스 토큰 생성
     * 2. 리프레시 토큰 생성
     * 3. 레디스에 토큰 등록
     * 4. 헤더에 엑세스 토큰 및 리프레시 토큰 추가
     * 5. MemberLoginResponseDto 정보 반환
     */
    public MemberLoginResponseDto login(HttpServletResponse response, String employeeNo, String password) {
        // 회원가입 여부 체크
        MemberDto memberDto = getMemberOrException(employeeNo);

        // 비밀번호 체크
        if (!encoder.matches(password, memberDto.password())) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }

        // 엑세스 토큰 생성
        AuthToken accessToken = tokenProvider.createAuthToken(employeeNo, memberDto.role().getCode(), accessTokenExpiry);
        // 리프레시 토큰 생성
        AuthToken refreshToken = tokenProvider.createAuthToken(employeeNo, refreshTokenExpiry);

        // Redis에 refreshToken 올리기
        refreshTokenCacheRepository.setRefreshToken(employeeNo, refreshToken.getToken(), refreshTokenExpiry);

        // 응답 헤더에 엑세스 토큰과 리프레시 토큰 추가
        HeaderUtils.addAccessToken(response, accessToken.getToken());
        HeaderUtils.addRefreshToken(response, refreshToken.getToken());

        return new MemberLoginResponseDto(memberDto.id(), memberDto.name(), memberDto.employeeNo(), memberDto.role());
    }

    public void logout(String employeeNo) {
        // 레디스에서 리프레시 토큰 삭제
         refreshTokenCacheRepository.deleteRefreshToken(employeeNo);
    }

    /**
     * 토큰 리프레시
     * 1. 헤더에서 리프레시 토큰 가져오기
     * 2. 유효한지 확인
     * 3. 새로운 액세스 토큰 발급
     * 4. 리프레시 토큰의 남은 기간을 가진 리프레시 토큰 재발급
     * 5. Redis 토큰 업데이트
     * 6. 액세스 토큰 및 리프레시 토큰 헤더에 추가
     * 7. MemberLoginResponseDto 반환
     */
    public MemberLoginResponseDto tokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        //refresh token
        String refreshToken = HeaderUtils.getRefreshToken(request);
        AuthToken authRefreshToken = tokenProvider.convertAuthRefreshToken(refreshToken);

        // 유효하지 않다면 익셉션
        try {
            authRefreshToken.validateOrException();
        } catch (ExpiredJwtException e) {
            throw new CommonException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new CommonException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String employeeNo = authRefreshToken.getMemberEmployeeNo();
        // 레디스에 없거나 등록된 토큰과 다르다면 익셉션
        if (!refreshTokenCacheRepository.existsRefreshToken(employeeNo) || !Objects.equals(refreshToken, refreshTokenCacheRepository.getRefreshToken(employeeNo))) {
            throw new CommonException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        MemberDto memberDto = getMemberOrException(employeeNo);

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(employeeNo, memberDto.role().getCode(), accessTokenExpiry);

        long validTime = authRefreshToken.extractClaims().getExpiration().getTime() - now.getTime();

        // 기존 refreshToken의 남은 시간의 유효기간을 가진 새로운 토큰 발급
        authRefreshToken = tokenProvider.createAuthToken(employeeNo, validTime);

        // 레디스 토큰 업데이트
        refreshTokenCacheRepository.setRefreshToken(employeeNo, authRefreshToken.getToken(), validTime);

        HeaderUtils.addAccessToken(response, newAccessToken.getToken());
        HeaderUtils.addRefreshToken(response, authRefreshToken.getToken());
        return new MemberLoginResponseDto(memberDto.id(), memberDto.name(), memberDto.employeeNo(), memberDto.role());
    }

    public MemberLoginResponseDto searchMyInfo(Authentication authentication) {
        String employeeNo = authentication.getName();
        MemberDto memberDto = getMemberOrException(employeeNo);
        return new MemberLoginResponseDto(memberDto.id(), memberDto.name(), memberDto.employeeNo(), memberDto.role());
    }

    public List<MemberResponseDto> searchName(String name) {
        return memberRepository.findAllByName(name).stream().map(MemberResponseDto::fromEntity).toList();
    }

    public MemberResponseDto searchEmployeeNo(String employeeNo) {
        return memberRepository.findByEmployeeNo(employeeNo)
                .map(MemberResponseDto::fromEntity)
                .orElse(null);
    }
}
