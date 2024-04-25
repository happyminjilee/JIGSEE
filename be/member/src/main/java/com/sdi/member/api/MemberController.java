package com.sdi.member.api;

import com.sdi.member.application.MemberService;
import com.sdi.member.dto.request.MemberLoginRequestDto;
import com.sdi.member.dto.response.MemberLoginResponseDto;
import com.sdi.member.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 로그인
     * 유저의 employeeNo, password를 입력하여 로그인을 진행한다. 이후 JWT 토큰 발급
     * @return MemberLoginResponse
     */
    @PostMapping("/login")
    public Response<MemberLoginResponseDto> login(HttpServletRequest request, HttpServletResponse response, @RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return Response.success(memberService.login(request, response, memberLoginRequestDto.employeeNo(), memberLoginRequestDto.password()));
    }

    /**
     * 로그아웃
     * 리프레시 토큰 정보 삭제.
     */
    @PostMapping("/member/logout")
    public Response<Void> logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        memberService.logout(request, response, authentication.getName());
        return Response.success();
    }

    /**
     * 토큰 리프레시
     * 액세스 토큰이 만료되었을 때 요청해야하는 API. 액세스 토큰이 유효하다면 액세스 토큰을 보내주고,
     * 만료 되었다면 리프레시 토큰이 있는지 확인 후 액세스 토큰을 발급해준다.
     * 리프레시 토큰 기간이 3일 이하라면, 리프레시 토큰도 생성한다.
     */
    @GetMapping("/refresh")
    public Response<MemberLoginResponseDto> tokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        return Response.success(memberService.tokenRefresh(request, response));
    }

    @GetMapping("/manager")
    public Response<Void> managerCheck() { return Response.success(); }

    @GetMapping("/engineer")
    public Response<Void> engineerCheck() { return Response.success(); }

    @GetMapping("/producer")
    public Response<Void> producerCheck() { return Response.success(); }
}
