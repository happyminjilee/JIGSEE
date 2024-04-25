package com.sdi.member.application;

import com.sdi.member.dto.MemberDto;
import com.sdi.member.dto.response.MemberLoginResponseDto;
import com.sdi.member.entity.RoleType;
import com.sdi.member.jwt.AuthToken;
import com.sdi.member.jwt.AuthTokenProvider;
import com.sdi.member.repository.MemberRepository;
import com.sdi.member.repository.RefreshTokenCacheRepository;
import com.sdi.member.util.CommonException;
import com.sdi.member.util.CookieUtils;
import com.sdi.member.util.ErrorCode;
import com.sdi.member.util.HeaderUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenCacheRepository refreshTokenCacheRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthTokenProvider tokenProvider;

    // 2시간
    private final long TOKEN_EXPIRY = 7200000;
    // 7일
    private final long REFRES_TOKEN_EXPIRY = 604800000;
    // 3일
    private static final long THREE_DAYS_MSEC = 259200000;
    private static final String REFRESH_TOKEN = "refresh_token";

    public Optional<MemberDto> loadMemberByEmployeeNo(String employeeNo) {
        return Optional.ofNullable(memberRepository.findByEmployeeNo(employeeNo)
                        .map(MemberDto::fromEntity)
                        .orElseThrow(() -> null));
    }

    /**
     * 가입 되어 있다면 멤버를 반환, 없다면 Exception 반환.
     */
    public MemberDto getMemberOrException(String employeeNo) {
        return memberRepository.findByEmployeeNo(employeeNo)
                .map(MemberDto::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with employee no: " + employeeNo));

    }

    /**
     * 로그인
     * 1. JWT 토큰 생성
     * 2. 리프레시 토큰 생성
     * 3. 기존 쿠키 삭제하고 새로 추가
     * 4. 레디스에 토큰 등록
     * 5. JWT 토큰 리턴
     */
    public MemberLoginResponseDto login(HttpServletRequest request, HttpServletResponse response, String employeeNo, String password) {
        // 회원가입 여부 체크
        MemberDto memberDto = getMemberOrException(employeeNo);

        // 비밀번호 체크
        if (!encoder.matches(password, memberDto.password())) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }

        // 엑세스 토큰 생성
        AuthToken accessToken = tokenProvider.createAuthToken(employeeNo, memberDto.role().getCode(), TOKEN_EXPIRY);
        // 리픠레시 토큰 생성
        AuthToken refreshToken = tokenProvider.createAuthToken(employeeNo, REFRES_TOKEN_EXPIRY);

        int cookieMaxAge = (int) REFRES_TOKEN_EXPIRY / 60;
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtils.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        // Redis에 refreshToken 올리기
        refreshTokenCacheRepository.setRefreshToken(employeeNo, refreshToken);

        return new MemberLoginResponseDto(memberDto.id(), memberDto.name(), memberDto.employeeNo(), memberDto.role(), accessToken.getToken());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, String employeeNo) {
        // 레디스에서 리프레시 토큰 삭제
         refreshTokenCacheRepository.deleteRefreshToken(employeeNo);

        // 헤더 토큰 삭제
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
    }

    /**
     * 토큰 리프레시
     * 1. 액세스 토큰 기존 헤더에서 가져오기
     * 2. 액세스 토큰 (String)-> (Token)으로 변환
     * 3. 유효한 토큰인지 검증
     * 4. 만료된 토큰인지 검증
     * 5. claims에서 이메일 가져오기
     * 6. Claims에서 role타입 가져오기
     * 7. 프론트로 부터 쿠키에서 리프레시 토큰 가져오기
     * 8. 유효한지 확인
     * 10. 새로운 액세스 토큰 발급
     * 11. 리프레시 토큰이 3일 이하로 남았을 경우 리프레시 토큰 갱신
     * 12. DB에 업데이트
     * 13. 액세스 토큰 리턴
     */
    public MemberLoginResponseDto tokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        // 1. 헤더로 부터 액세스 토큰 가져오기
        String accessTokenStr = HeaderUtils.getAccessToken(request);
        AuthToken accessToken = tokenProvider.convertAuthAccessToken(accessTokenStr);

        // 2-1. 토큰이 유효한지 체크
        if (accessToken.validate()) {
            // 유효하다면 지금 토큰 그대로 반환
            String employeeNo = accessToken.getMemberEmployeeNo();
            MemberDto memberDto = loadMemberByEmployeeNo(employeeNo).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with employee no: " + employeeNo)
            );
            return new MemberLoginResponseDto(memberDto.id(), memberDto.name(), memberDto.employeeNo(), memberDto.role(), accessToken.getToken());
        }

        // 액세스 토큰 기간 만료 및 기타 유효성 검사
        try {
            accessToken.validateOrException();
        } catch (ExpiredJwtException e) {
            log.info("Invalid JWT token.");
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new CommonException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        // 2-2. 토큰이 유효하지 않다면 리프레시 토큰이 있는지 확인하자, 만료되었을 경우 만료 토큰을 가져옴.
        Claims claims = accessToken.getExpiredClaims();

        // 토큰이 유효하다면, 지금 토큰 그대로 반환
        if (claims == null) {
            String employeeNo = accessToken.getMemberEmployeeNo();
            MemberDto memberDto = loadMemberByEmployeeNo(employeeNo).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with employee no: " + employeeNo)
            );
            return new MemberLoginResponseDto(memberDto.id(), memberDto.name(), memberDto.employeeNo(), memberDto.role(), accessToken.getToken());
        }

        RoleType roleType = RoleType.of(claims.get("role", String.class));

        //refresh token
        String refreshToken = CookieUtils.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);
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

        // 레디스에 없다면 익셉션
        if (!refreshTokenCacheRepository.existsRefreshToken(employeeNo)) {
            throw new CommonException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        MemberDto memberDto = loadMemberByEmployeeNo(employeeNo).orElseThrow(
                () -> new UsernameNotFoundException("User not found with employee no: " + employeeNo)
        );

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(employeeNo, roleType.getCode(), TOKEN_EXPIRY);

        long validTime = authRefreshToken.extractClaims().getExpiration().getTime() - now.getTime();

        //refresh 토큰 기간이 3일 이하일 경우 새로 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            authRefreshToken = tokenProvider.createAuthToken(employeeNo, REFRES_TOKEN_EXPIRY);
            refreshTokenCacheRepository.updateRefreshToken(employeeNo, authRefreshToken);

            int cookieMaxAge = (int) REFRES_TOKEN_EXPIRY / 60;
            CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtils.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return new MemberLoginResponseDto(memberDto.id(), memberDto.name(), memberDto.employeeNo(), memberDto.role(), newAccessToken.getToken());
    }
}
