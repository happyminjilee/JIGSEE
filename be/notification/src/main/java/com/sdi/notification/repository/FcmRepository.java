package com.sdi.notification.repository;

import com.sdi.notification.entity.FcmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcmRepository extends JpaRepository<FcmEntity, Long> {
    Optional<FcmEntity> findByEmployeeNo(String employeeNo);
    Optional<List<FcmEntity>> findAllByEmployeeNoIn(List<String> employeeNos);
}
