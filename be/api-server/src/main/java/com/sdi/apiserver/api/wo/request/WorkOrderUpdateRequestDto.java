package com.sdi.apiserver.api.wo.request;

import com.sdi.apiserver.util.CheckDoneList;
import lombok.Value;

import java.util.List;

@Value
public class WorkOrderUpdateRequestDto {

    Long id;
    List<CheckDoneList> checkList;
}
