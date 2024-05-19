package com.sdi.apiserver.api.member.dto.response;

public record LoginResponseDto(
        Long id,
        String name,
        String employeeNo,
        String role
) {

    public static LoginResponseDto from(Long id,
                                        String name,
                                        String employeeNo,
                                        String role){
        return new LoginResponseDto(id, name, employeeNo, role);
    }
}
