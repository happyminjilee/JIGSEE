package com.sdi.member.api;

import com.sdi.member.application.MemberService;
import com.sdi.member.dto.request.MemberLoginRequestDto;
import com.sdi.member.dto.request.MemberSearchByMemberListRequestDto;
import com.sdi.member.dto.response.MemberEmailResponseDto;
import com.sdi.member.dto.response.MemberLoginResponseDto;
import com.sdi.member.dto.response.MemberResponseDto;
import com.sdi.member.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
class MemberController {
    private final MemberService memberService;

    /**
     * 로그인
     * 유저의 employeeNo, password를 입력하여 로그인을 진행한다. 이후 JWT 토큰 발급
     * @return MemberLoginResponse
     */
    @PostMapping("/login")
    Response<MemberLoginResponseDto> login(HttpServletResponse response, @RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return Response.success(memberService.login(response, memberLoginRequestDto.employeeNo(), memberLoginRequestDto.password()));
    }

    /**
     * 로그아웃
     * 리프레시 토큰 정보 삭제.
     */
    @PostMapping("/member/logout")
    Response<Void> logout(Authentication authentication) {
        memberService.logout(authentication.getName());
        return Response.success();
    }

    /**
     * 토큰 리프레시
     * 액세스 토큰이 만료되었을 때 요청해야하는 API. 액세스 토큰이 유효하다면 액세스 토큰을 보내주고,
     * 만료 되었다면 리프레시 토큰이 있는지 확인 후 액세스 토큰을 발급해준다.
     * 리프레시 토큰 기간이 3일 이하라면, 리프레시 토큰도 생성한다.
     */
    @PostMapping("/refresh")
    Response<MemberLoginResponseDto> tokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        return Response.success(memberService.tokenRefresh(request, response));
    }

    @GetMapping("/member/search")
    Response<MemberLoginResponseDto> searchMyInfo(Authentication authentication) {
        return Response.success(memberService.searchMyInfo(authentication));
    }

    @GetMapping("/member/email")
    Response<MemberEmailResponseDto> searchMyEmail(Authentication authentication) {
        return Response.success(memberService.searchMyEmail(authentication));
    }

    @GetMapping("/member/search/name")
    Response<List<MemberResponseDto>> searchName(@RequestParam("name") String name) {
        return Response.success(memberService.searchName(name));
    }

    @GetMapping("/member/search/employee-no")
    Response<MemberResponseDto> searchEmployeeNo(@RequestParam("employee-no") String employeeNo) {
        return Response.success(memberService.searchEmployeeNo(employeeNo));
    }

    @GetMapping("/search/role")
    Response<List<MemberResponseDto>> searchRole(@RequestParam("role") String role) {
        return Response.success(memberService.searchRole(role));
    }

    @GetMapping("/manager")
    Response<Void> managerCheck() { return Response.success(); }

    @GetMapping("/engineer")
    Response<Void> engineerCheck() { return Response.success(); }

    @GetMapping("/producer")
    Response<Void> producerCheck() { return Response.success(); }

    @PostMapping("/member/search/name/list")
    Response<List<MemberResponseDto>> searchByNameList(@RequestBody MemberSearchByMemberListRequestDto dto){
        return Response.success(memberService.searchByNameList(dto.list()));
    }
}
