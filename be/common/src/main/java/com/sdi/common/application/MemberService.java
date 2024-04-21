package com.sdi.common.application;

import com.sdi.common.domain.MemberRefreshToken;
import com.sdi.common.domain.RoleType;
import com.sdi.common.dto.Member;
import com.sdi.common.dto.response.MemberLoginResponse;
import com.sdi.common.jwt.*;
import com.sdi.common.repository.MemberRefreshTokenRepository;
import com.sdi.common.repository.MemberRepository;
import com.sdi.common.util.CommonException;
import com.sdi.common.util.CookieUtils;
import com.sdi.common.util.ErrorCode;
import com.sdi.common.util.HeaderUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthTokenProvider tokenProvider;

    @Value("${jwt.secret}")
    private String key;

    // 2시간
    private final long TOKEN_EXPIRY = 7200000;
    // 7일
    private final long REFRES_TOKEN_EXPIRY = 604800000;
    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    public Optional<Member> loadMemberByEmployeeNo(String employeeNo) {
        return Optional.ofNullable(memberRepository.findByEmployeeNo(employeeNo)
                .map(Member::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with employee no: " + employeeNo)));
//        레디스 이용 시 필요 코드
//        return Optional.ofNullable(MemberRepository.getMember(employeeNo)
//                .orElseGet(() ->
//                        memberRepository.findByEmployeeNo(employeeNo)
//                                .map(Member::fromEntity)
//                                .orElseThrow(() -> null)
//                )
//        );
    }

    /**
     * 가입 되어 있다면 멤버를 반환, 없다면 Exception 반환.
     */
    public Member getMemberOrException(String employeeNo) {
        return memberRepository.findByEmployeeNo(employeeNo)
                .map(Member::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with employee no: " + employeeNo));

    }

    /**
     * 로그인
     * 1. JWT 토큰 생성
     * 2. 리프레시 토큰 생성
     * 3. 유저 - 리프레시 토큰이 있는지 확인
     * 4. 없으면 새로 등록
     * 5. 있으면 DB에 업데이트
     * 6. 기존 쿠키 삭제하고 새로 추가
     * 7. JWT 토큰 리턴
     */
    public MemberLoginResponse login(HttpServletRequest request, HttpServletResponse response, String employeeNo, String password) {
        // 회원가입 여부 체크
        Member member = getMemberOrException(employeeNo);

        // 비밀번호 체크
        if (!encoder.matches(password, member.password())) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }

        AuthToken accessToken = tokenProvider.createAuthToken(employeeNo, member.role(), TOKEN_EXPIRY);
        AuthToken refreshToken = tokenProvider.createAuthToken(employeeNo, REFRES_TOKEN_EXPIRY);

        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByMemberEmployeeNo(employeeNo);
        if (memberRefreshToken == null) { // 토큰이 없는 경우. 새로 등록
            memberRefreshToken = new MemberRefreshToken(employeeNo, refreshToken.getToken());
            memberRefreshTokenRepository.saveAndFlush(memberRefreshToken);
        } else {  // 토큰 발견 -> 리프레시 토큰 업데이트
            memberRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) REFRES_TOKEN_EXPIRY / 60;
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtils.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return new MemberLoginResponse(member.id(), member.name(), member.employeeNo(), member.role(), accessToken.getToken());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, String employeeNo) {
        // 레디스에서 삭제(레디스 이용 시)
        // memberCacheRepository.deletemember(employeeNo);

        // 리프레시 토큰 정보 삭제
        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByMemberEmployeeNo(employeeNo);
        memberRefreshTokenRepository.delete(memberRefreshToken);

        // 헤더 토큰 삭제
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
    }

    /**
     * 리프레시 토큰
     * 1. 액세스 토큰 기존 헤더에서 가져오기
     * 2. 액세스 토큰 (String)-> (Token)으로 변환
     * 3. 유효한 토큰인지 검증
     * 4. 만료된 토큰인지 검증
     * 5. claims에서 이메일 가져오기
     * 6. Claims에서 role타입 가져오기
     * 7. 프론트로 부터 쿠키에서 리프레시 토큰 가져오기
     * 8. 유효한지 확인
     * 9. 유저-리프레시 레포에서 토큰 가져오기 - 없을 경우 에러 - 로그인할 때 만들어지기 때문에 없을리 없음
     * 10. 새로운 액세스 토큰 발급
     * 11. 리프레시 토큰이 3일 이하로 남았을 경우 리프레시 토큰 갱신
     * 12. DB에 업데이트
     * 13. 액세스 토큰 리턴
     */
    public MemberLoginResponse refreshToken(String employeeNo, HttpServletRequest request, HttpServletResponse response) {
        // 1. 헤더로 부터 액세스 토큰 가져오기
        String accessToken = HeaderUtils.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        Member member = loadMemberByEmployeeNo(employeeNo).orElseThrow(
                () -> new UsernameNotFoundException("User not found with employee no: " + employeeNo)
        );

        // 2-1. 토큰이 유효한지 체크
        if (authToken.validate()) {
            // 유효하다면 지금 토큰 그대로 반환
            return new MemberLoginResponse(member.id(), member.name(), member.employeeNo(), member.role(), authToken.getToken());
        }

        // 2-2. 토큰이 유효하지 않다면 리프레시 토큰이 있는지 확인하자, 만료되었을 경우 만료 토큰을 가져옴.
        Claims claims = authToken.getExpiredClaims();

        // 토큰이 유효하다면, 지금 토큰 그대로 반환
        if (claims == null) {
            return new MemberLoginResponse(member.id(), member.name(), member.employeeNo(), member.role(), accessToken);
        }

        RoleType roleType = RoleType.of(claims.get("role", String.class));

        //refresh token
        String refreshToken = CookieUtils.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        // 유효하지 않다면, 익셉션
        if (!authRefreshToken.validate()) {
            throw new CommonException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // DB 확인 익셉션
        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByMemberEmployeeNoAndRefreshToken(employeeNo, refreshToken);
        if (memberRefreshToken == null) {
            throw new CommonException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(employeeNo, roleType.getCode(), TOKEN_EXPIRY);

        long validTime = authRefreshToken.extractClaims().getExpiration().getTime() - now.getTime();

        //refresh 토큰 기간이 3일 이하일 경우 새로 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            authRefreshToken = tokenProvider.createAuthToken(key, REFRES_TOKEN_EXPIRY);
            memberRefreshToken.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) REFRES_TOKEN_EXPIRY / 60;
            CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtils.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return new MemberLoginResponse(member.id(), member.name(), member.employeeNo(), member.role(), newAccessToken.getToken());
    }
}
