package com.sdi.notificationapi.repository;

import com.sdi.notificationapi.entity.RepairRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRequestsRepository extends MongoRepository<RepairRequestEntity, String> {
}
