package com.sdi.common.util;

import com.sdi.common.api.dto.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "memberClient", url = "http://localhost:8081") // 요청을 보낼 url 작성(멤버)
public interface MemberClient {
    @GetMapping("/member/search")
    MemberResponseDto getMember(@RequestHeader("Authorization") String accessToken);
}