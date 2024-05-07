package com.sdi.notificationapi.service;

import com.sdi.notificationapi.dto.MemberInfoDto;
import com.sdi.notificationapi.util.MemberClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApiService {
    private final MemberClient memberClient;

    public MemberInfoDto getCurrentMember(String accessToken) {
        return memberClient.getMember(accessToken).getResult();
    }
}
