package com.sdi.work_order.application;

import com.sdi.work_order.client.MemberClient;
import com.sdi.work_order.client.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static final String NAME_FORMAT = "%s,%s";

    private final MemberClient memberClient;

    public String getMemberInfo(String accessToken, String employeeNo) {
        return toMemberInfo(getMemberResponseDtoByEmployeeNo(accessToken, employeeNo));
    }

    public String getMemberEmployeeNo(String accessToken) {
        return getMemberResponseDtoByAccessToken(accessToken).employeeNo();
    }

    private MemberResponseDto getMemberResponseDtoByAccessToken(String accessToken) {
        return memberClient.findMemberByToken(accessToken).getResult();
    }

    private MemberResponseDto getMemberResponseDtoByEmployeeNo(String accessToken, String employeeNo){
        return memberClient.findMemberByEmployeeNo(accessToken, employeeNo).getResult();
    }

    private String toMemberInfo(MemberResponseDto dto) {
        return String.format(NAME_FORMAT, dto.name(), dto.employeeNo());
    }
}
