package com.sdi.notification.service;

import com.sdi.notification.dto.MemberResponseDto;
import com.sdi.notification.util.MemberClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiService {
    private final MemberClient memberClient;

    public MemberResponseDto getMember(String accessToken) {
        return memberClient.getMember(accessToken);
    }
}
