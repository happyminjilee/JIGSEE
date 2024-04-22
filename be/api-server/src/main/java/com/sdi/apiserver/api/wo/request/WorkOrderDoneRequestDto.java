package com.sdi.apiserver.api.wo.request;

import com.sdi.apiserver.util.CheckDoneList;
import lombok.Value;

import java.util.List;

@Value
public class WorkOrderDoneRequestDto {

    Long id;
    List<CheckDoneList> checkList;
    Boolean canUse;
}
