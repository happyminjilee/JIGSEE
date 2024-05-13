package com.sdi.work_order.repository;

import com.sdi.work_order.entity.WorkOrderRDBEntity;
import com.sdi.work_order.util.WorkOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface WorkOrderRDBRepository extends JpaRepository<WorkOrderRDBEntity, Long> {


    Page<WorkOrderRDBEntity> findAllByCreatorEmployeeNoOrderByCreatedAtDesc(String creatorEmployeeNo, Pageable pageable);

    Page<WorkOrderRDBEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<WorkOrderRDBEntity> findByStatusOrderByCreatedAtDesc(WorkOrderStatus status, Pageable pageable);

    Optional<WorkOrderRDBEntity> findByJigSerialNoAndStatusNot(String jigSerialNo, WorkOrderStatus status);

    List<WorkOrderRDBEntity> findAllByCreatorEmployeeNoAndStatusAndCreatedAtBetween(String employeeNo, WorkOrderStatus workOrderStatus, LocalDateTime startDate, LocalDateTime endDate);

    int countByCreatorEmployeeNoAndStatus(String employeeNo, WorkOrderStatus workOrderStatus);

    int countByStatusNotAndCreatedAtBetween(WorkOrderStatus workOrderStatus, LocalDateTime startDate, LocalDateTime endDate);
}
