package com.sdi.notification.util;

import com.sdi.notification.dto.MemberInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "memberClient", url = "${apis.api-base-url}") // 요청을 보낼 url 작성(멤버)
public interface MemberClient {
    String ACCESS_TOKEN_PREFIX = "Authorization";

    @GetMapping("/member/search")
    Response<MemberInfoDto> getMember(@RequestHeader(ACCESS_TOKEN_PREFIX) String accessToken);

    @GetMapping("/member/role")
    Response<List<MemberInfoDto>> getMembersInRole(@RequestParam("role") String role);
}