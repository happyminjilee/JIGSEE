package com.sdi.notification.repository;

import com.sdi.notification.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    void deleteByEmployeeNo(String employeeNo);
}
