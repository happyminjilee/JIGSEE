package com.sdi.member.dto.request;

import java.util.List;

public record MemberSearchByMemberListRequestDto(
        List<String> list
) {

}
