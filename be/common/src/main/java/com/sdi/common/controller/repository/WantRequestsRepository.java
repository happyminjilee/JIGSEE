package com.sdi.common.controller.repository;

import com.sdi.common.entity.WantRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantRequestsRepository extends MongoRepository<WantRequestEntity, String> {

}
