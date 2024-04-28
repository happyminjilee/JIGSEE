package com.sdi.work_order.repository;

import com.sdi.work_order.entity.WorkOrderRDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRDBRepository extends JpaRepository<WorkOrderRDBEntity, Long> {
}
