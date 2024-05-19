package com.sdi.notificationapi.repository;

import com.sdi.notificationapi.entity.WantRequestEntity;
import com.sdi.notificationapi.util.JigRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantRequestsRepository extends MongoRepository<WantRequestEntity, String> {
    Page<WantRequestEntity> findAllByIsAcceptAndStatus(Boolean isAccept, JigRequestStatus status, Pageable pageable);
    Page<WantRequestEntity> findAllByStatus(JigRequestStatus status, Pageable pageable);
}

