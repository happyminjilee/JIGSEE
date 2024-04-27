package com.sdi.apiserver.api.member;

import com.sdi.apiserver.api.member.dto.request.LoginRequestDto;
import com.sdi.apiserver.api.member.dto.response.LoginResponseDto;
import com.sdi.apiserver.api.member.dto.response.LogoutResponseDto;
import com.sdi.apiserver.api.member.dto.response.MemberResponseDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class MemberController {

    @PostMapping("/login")
    Response<LoginResponseDto> login(@RequestBody LoginRequestDto dto, HttpServletResponse response) {
        response.addHeader("access_token", "a1");
        response.addHeader("refresh_token", "r1");
        LoginResponseDto responseDto = LoginResponseDto.from(1L, "t", "t", "t");
        return Response.success(responseDto);
    }

    @PostMapping("/member/logout")
    Response<LogoutResponseDto> logout() {
        LogoutResponseDto dto = LogoutResponseDto.from("result");
        return Response.success(dto);
    }

    @GetMapping("/refresh")
    Response<LoginResponseDto> refresh(HttpServletResponse response){
        response.addHeader("access_token", "a1");
        response.addHeader("refresh_token", "r1");
        LoginResponseDto dto = LoginResponseDto.from(1L, "t", "t", "t");
        return Response.success(dto);
    }

    @GetMapping("/member/search/name")
    Response<MemberResponseDto> searchByName(@RequestParam(name = "name") String name) {
        MemberResponseDto dto = new MemberResponseDto("testName", "testNo");
        return Response.success(dto);
    }

    @GetMapping("/member/search/employee-no")
    Response<MemberResponseDto> searchByEmployeeNo(@RequestParam(name = "employee-no") String employeeNo) {
        MemberResponseDto dto = new MemberResponseDto("testName", "testNo");
        return Response.success(dto);
    }
}
