package com.sdi.work_order.entity;

import com.sdi.work_order.util.WorkOrderCheckList;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "work_orders")
public class WorkOrderNosqlEntity {

    @Id
    private String id; // rdb의 work-order의 id 값과 일치 -> id로 조회
    private Boolean passOrNot; // 전체 통과 유무
    private List<WorkOrderCheckList> checkList;
}
