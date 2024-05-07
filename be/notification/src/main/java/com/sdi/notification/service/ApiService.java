package com.sdi.notification.service;

import com.sdi.notification.dto.MemberInfoDto;
import com.sdi.notification.util.MemberClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiService {
    private final MemberClient memberClient;

    public MemberInfoDto getMember(String accessToken) {
        return memberClient.getMember(accessToken).getResult();
    }

    public List<MemberInfoDto> getMembersInRole(String role) {
        return memberClient.getMembersInRole(role).getResult();
    }
}
