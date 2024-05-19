package com.sdi.apiserver.api.wo.dto.request;

import com.sdi.apiserver.util.WorkOrderCheckItem;
import lombok.Value;

import java.util.List;

@Value
public class WorkOrderDoneRequestDto {

    Long id;
    List<WorkOrderCheckItem> checkList;
}
