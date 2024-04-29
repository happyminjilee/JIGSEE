package com.sdi.work_order.entity;

import com.sdi.work_order.util.WorkOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "work_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkOrderRDBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_employee_no", length = 50)
    private String creatorEmployeeNo;

    @Column(name = "terminator_employee_no", length = 50)
    private String terminatorEmployeeNo;

    @Column(name = "jigSerialNo", length = 50)
    private String jigSerialNo;

    @Column(length = 50)
    private String model;

    @Column
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    @Column(name = "check_list_id")
    private String checkListId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static WorkOrderRDBEntity from(String creatorEmployeeNo, String jigSerialNo, String model, String checkListId){
        return new WorkOrderRDBEntity(null,
                creatorEmployeeNo,
                null,
                jigSerialNo,
                model,
                WorkOrderStatus.PROGRESS,
                checkListId,
                LocalDateTime.now(),
                null
        );
    }

    public void updateStatus(WorkOrderStatus status){
        this.status = status;
    }
}
