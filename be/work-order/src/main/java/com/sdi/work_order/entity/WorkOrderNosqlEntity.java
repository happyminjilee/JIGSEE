package com.sdi.work_order.entity;

import com.sdi.work_order.util.WorkOrderCheckItem;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "work_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkOrderNosqlEntity {

    @Id
    private String id;
    private Boolean passOrNot; // 전체 통과 유무
    private List<WorkOrderCheckItem> checkList;

    public static WorkOrderNosqlEntity from(String id, Boolean passOrNot, List<WorkOrderCheckItem> checkList){
        return new WorkOrderNosqlEntity(id, passOrNot, checkList);
    }

    public void updateCheckList(List<WorkOrderCheckItem> checkList){
        this.checkList = checkList;
    }
}
