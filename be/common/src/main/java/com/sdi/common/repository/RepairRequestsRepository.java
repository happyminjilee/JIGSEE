package com.sdi.common.controller.repository;

import com.sdi.common.entity.RepairRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRequestsRepository extends MongoRepository<RepairRequestEntity, String> {
}
