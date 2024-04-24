package com.sdi.apiserver.api.member;

import com.sdi.apiserver.api.member.dto.request.LoginRequestDto;
import com.sdi.apiserver.api.member.dto.response.MemberResponseDto;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class MemberController {

    @PostMapping("/login")
    Response<Void> login(@RequestBody LoginRequestDto dto) {
        return Response.success();
    }

    @PostMapping("/member/logout")
    Response<Void> logout() {
        return Response.success();
    }

    @GetMapping("/member")
    Response<MemberResponseDto> searchByName(@RequestParam(name = "name") String name) {
        MemberResponseDto dto = new MemberResponseDto("testName", "testNo");
        return Response.success(dto);
    }
}
