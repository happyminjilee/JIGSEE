package com.sdi.common.repository;

import com.sdi.common.entity.WantResponseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantResponsesRepository extends MongoRepository<WantResponseEntity, String> {
}
