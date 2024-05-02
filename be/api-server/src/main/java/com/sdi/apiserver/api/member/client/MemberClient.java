package com.sdi.apiserver.api.member.client;

import com.sdi.apiserver.api.member.dto.request.LoginRequestDto;
import com.sdi.apiserver.api.member.dto.response.LoginResponseDto;
import com.sdi.apiserver.api.member.dto.response.MemberResponseDto;
import com.sdi.apiserver.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "memberClient", url = "${apis.member-base-url}")
public interface MemberClient {
    @PostMapping("/login")
    feign.Response login(@RequestBody LoginRequestDto loginRequestDto);

    @PostMapping("/member/logout")
    Response<Void> logout(@RequestHeader("Authorization") String accessToken);

    @PostMapping("/refresh")
    feign.Response tokenRefresh(@RequestHeader("RefreshToken") String refreshToken);

    @GetMapping("/member/search")
    Response<LoginResponseDto> searchMyInfo(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/member/search/name")
    Response<MemberResponseDto> searchName(@RequestHeader("Authorization") String accessToken, @RequestParam("name") String name);

    @GetMapping("/member/search/employee-no")
    Response<MemberResponseDto> searchEmployeeNo(@RequestHeader("Authorization") String accessToken, @RequestParam("employee-no") String employeeNo);

    @GetMapping("/manager")
    Response<Void> managerCheck(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/engineer")
    Response<Void> engineerCheck(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/producer")
    Response<Void> producerCheck(@RequestHeader("Authorization") String accessToken);

}
