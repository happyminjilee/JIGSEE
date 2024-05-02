package com.sdi.notification.repository;

import com.sdi.notification.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    Optional<List<NotificationEntity>> findAllByReceiverAndCheckStatusIsTrueOrderByIdDesc(String receiver);
    Page<NotificationEntity> findAllByReceiverOrderByIdDesc(String receiver, Pageable pageable);
}
