package com.sdi.common.repository;

import com.sdi.common.entity.WantRequestEntity;
import com.sdi.common.util.JigRequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WantRequestsRepository extends MongoRepository<WantRequestEntity, String> {
    Page<WantRequestEntity> findAllByIsAcceptAndStatus(Boolean isAccept, JigRequestStatus status, Pageable pageable);
    Page<WantRequestEntity> findAllByStatus(JigRequestStatus status, Pageable pageable);
}

