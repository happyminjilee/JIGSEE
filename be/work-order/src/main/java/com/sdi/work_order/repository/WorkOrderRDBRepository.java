package com.sdi.work_order.repository;

import com.sdi.work_order.entity.WorkOrderRDBEntity;
import com.sdi.work_order.util.WorkOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkOrderRDBRepository extends JpaRepository<WorkOrderRDBEntity, Long> {
    Page<WorkOrderRDBEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<WorkOrderRDBEntity> findByStatusOrderByCreatedAtDesc(WorkOrderStatus status, Pageable pageable);
}
