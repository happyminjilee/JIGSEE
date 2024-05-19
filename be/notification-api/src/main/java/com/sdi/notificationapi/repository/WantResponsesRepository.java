package com.sdi.notificationapi.repository;

import com.sdi.notificationapi.entity.WantResponseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantResponsesRepository extends MongoRepository<WantResponseEntity, String> {
}
