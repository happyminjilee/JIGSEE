package com.sdi.common.repository;

import com.sdi.common.entity.WantRequestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WantRequestsRepository extends MongoRepository<WantRequestEntity, String> {
    Page<List<WantRequestEntity>> findAllByIsAcceptAndStatus(boolean isAccept, String status, Pageable pageable);
    Page<List<WantRequestEntity>> findAllByStatus(String status, Pageable pageable);

}

