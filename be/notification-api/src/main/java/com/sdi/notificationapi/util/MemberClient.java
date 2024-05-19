package com.sdi.notificationapi.util;

import com.sdi.notificationapi.dto.MemberInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "memberClient", url = "${apis.api-base-url}")
public interface MemberClient {
    @GetMapping("/member/search")
    Response<MemberInfoDto> getMember(@RequestHeader("Authorization") String accessToken);
}
