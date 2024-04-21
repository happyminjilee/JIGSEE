package com.sdi.common.repository;

import com.sdi.common.domain.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
    MemberRefreshToken findByMemberEmployeeNoAndRefreshToken(String employeeNo, String refreshToken);
    MemberRefreshToken findByMemberEmployeeNo(String employeeNo);
}
