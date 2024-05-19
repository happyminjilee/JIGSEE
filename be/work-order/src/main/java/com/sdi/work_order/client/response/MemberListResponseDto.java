package com.sdi.work_order.client.response;

import java.util.List;

public record MemberListResponseDto(
        List<MemberResponseDto> list
) {
    public static MemberListResponseDto from(List<MemberResponseDto> list){
        return new MemberListResponseDto(list);
    }
}
