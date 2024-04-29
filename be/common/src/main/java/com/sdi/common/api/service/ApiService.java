package com.sdi.common.api.service;

import com.sdi.common.api.dto.MemberResponseDto;
import com.sdi.common.util.MemberClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiService {
    private MemberClient memberClient;

    public MemberResponseDto getMember(String accessToken) {
        return memberClient.getMember(accessToken);
    }
}
