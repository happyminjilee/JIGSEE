package com.sdi.apiserver.api.member.dto.response;

public record LogoutResponseDto(
        String message
) {
    public static LogoutResponseDto from(String message){
        return new LogoutResponseDto(message);
    }
}
