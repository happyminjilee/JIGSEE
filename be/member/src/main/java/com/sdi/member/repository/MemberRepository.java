package com.sdi.member.repository;

import com.sdi.member.entity.MemberEntity;
import com.sdi.member.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmployeeNo(String employeeNo);
    Optional<MemberEntity> findByName(String name);
    List<MemberEntity> findAllByName(String name);
    List<MemberEntity> findAllByRole(RoleType roleType);
}
