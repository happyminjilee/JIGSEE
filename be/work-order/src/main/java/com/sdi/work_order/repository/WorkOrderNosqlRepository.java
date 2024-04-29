package com.sdi.work_order.repository;

import com.sdi.work_order.entity.WorkOrderNosqlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkOrderNosqlRepository extends MongoRepository<WorkOrderNosqlEntity, String> {
}
