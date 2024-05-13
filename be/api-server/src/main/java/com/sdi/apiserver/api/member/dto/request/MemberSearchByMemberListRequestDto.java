package com.sdi.apiserver.api.member.dto.request;

import java.util.List;

public record MemberSearchByMemberListRequestDto(
        List<String> names
) {
}
