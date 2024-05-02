package com.sdi.work_order.client;

import com.sdi.work_order.client.response.MemberResponseDto;
import com.sdi.work_order.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "memberClient", url = "${apis.api-base-url}")
public interface MemberClient {

    @GetMapping("/member/search")
    Response<MemberResponseDto> findMemberByToken(@RequestHeader(name = "Authorization") String accessToken);


    @GetMapping("/member/search/employee-no")
    Response<MemberResponseDto> findMemberByEmployeeNo(@RequestHeader(name = "Authorization") String accessToken,
                                                       @RequestParam(name = "employee-no") String employeeNo);
}
