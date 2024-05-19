package com.sdi.work_order.client.response;

public record MemberResponseDto(
        String name,
        String employeeNo
) {
    public static MemberResponseDto of(String name, String employeeNo){
        return new MemberResponseDto(name, employeeNo);
    }
}
