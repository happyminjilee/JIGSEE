package com.sdi.apiserver.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.apiserver.api.member.client.MemberClient;
import com.sdi.apiserver.api.member.dto.request.LoginRequestDto;
import com.sdi.apiserver.api.member.dto.response.LoginResponseDto;
import com.sdi.apiserver.api.member.dto.response.MemberResponseDto;
import com.sdi.apiserver.util.CommonException;
import com.sdi.apiserver.util.HeaderUtils;
import com.sdi.apiserver.util.Response;
import feign.Headers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
class MemberController {

    private final MemberClient memberClient;

    @PostMapping("/login")
    Response<LoginResponseDto> login(@RequestBody LoginRequestDto dto, HttpServletResponse httpServletResponse) throws IOException {

        feign.Response backResponse = memberClient.login(dto);

        Map<String, Collection<String>> headers = backResponse.headers();
        InputStream inputStream = backResponse.body().asInputStream();

        String accessToken = headers.get("Authorization").iterator().next();
        String refreshToken = headers.get("RefreshToken").iterator().next();

        // 양쪽의 []를 제거하고 헤더에 추가
        HeaderUtils.addAccessToken(httpServletResponse, accessToken.replace("[", "").replace("]", ""));
        HeaderUtils.addRefreshToken(httpServletResponse, refreshToken.replace("[", "").replace("]", ""));

        ObjectMapper mapper = new ObjectMapper();
        Response response = mapper.readValue(inputStream, Response.class);

        return response;
    }

    @PostMapping("/member/logout")
    Response<Void> logout(HttpServletRequest httpServletRequest) {
        String accessToken = HeaderUtils.getAccessToken(httpServletRequest);
        return memberClient.logout(accessToken);
    }

    @PostMapping("/refresh")
    Response<LoginResponseDto> refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String refreshToken = HeaderUtils.getRefreshToken(httpServletRequest);

        feign.Response backResponse = memberClient.tokenRefresh(refreshToken);

        InputStream inputStream = backResponse.body().asInputStream();
        Map<String, Collection<String>> headers = backResponse.headers();

        String accessToken = headers.get("Authorization").iterator().next();
        refreshToken = headers.get("RefreshToken").iterator().next();

        // 양쪽의 []를 제거하고 헤더에 추가
        HeaderUtils.addAccessToken(httpServletResponse, accessToken.replace("[", "").replace("]", ""));
        HeaderUtils.addRefreshToken(httpServletResponse, refreshToken.replace("[", "").replace("]", ""));

        ObjectMapper mapper = new ObjectMapper();
        Response response = mapper.readValue(inputStream, Response.class);

        return response;
    }

    @GetMapping("/member/search")
    Response<LoginResponseDto> searchMyInfo(HttpServletRequest httpServletRequest) {
        String accessToken = HeaderUtils.getAccessToken(httpServletRequest);
        return memberClient.searchMyInfo(accessToken);
    }

    @GetMapping("/member/search/name")
    Response<MemberResponseDto> searchByName(HttpServletRequest httpServletRequest, @RequestParam(name = "name") String name) {
        String accessToken = HeaderUtils.getAccessToken(httpServletRequest);
        return memberClient.searchName(accessToken, name);
    }

    @GetMapping("/member/search/employee-no")
    Response<MemberResponseDto> searchByEmployeeNo(HttpServletRequest httpServletRequest, @RequestParam(name = "employee-no") String employeeNo) {
        String accessToken = HeaderUtils.getAccessToken(httpServletRequest);
        return memberClient.searchEmployeeNo(accessToken, employeeNo);
    }

    @GetMapping("/manager")
    Response<Void> managerCheck(HttpServletRequest request) {
        String accessToken = HeaderUtils.getAccessToken(request);
        return memberClient.managerCheck(accessToken);
    }

    @GetMapping("/engineer")
    Response<Void> engineerCheck(HttpServletRequest request) {
        String accessToken = HeaderUtils.getAccessToken(request);
        return memberClient.engineerCheck(accessToken);
    }

    @GetMapping("/producer")
    Response<Void> producerCheck(HttpServletRequest request) {
        String accessToken = HeaderUtils.getAccessToken(request);
        return memberClient.producerCheck(accessToken);
    }
}
