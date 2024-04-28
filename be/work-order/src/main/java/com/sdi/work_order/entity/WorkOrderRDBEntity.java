package com.sdi.work_order.entity;

import com.sdi.work_order.util.WorkOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    public static WorkOrderRDBEntity from(String creatorEmployeeNo, String jigSerialNo){
        return new WorkOrderRDBEntity(null, creatorEmployeeNo, null, jigSerialNo, WorkOrderStatus.PROGRESS);
    }
}
