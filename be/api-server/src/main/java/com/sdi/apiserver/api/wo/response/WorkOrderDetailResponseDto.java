package com.sdi.apiserver.api.wo.response;

import com.sdi.apiserver.util.CheckDoneList;
import lombok.Value;

import java.util.List;

@Value
public class WorkOrderDetailResponseDto {

    Long id;
    String creator;
    String terminator;
    String model;
    String serialNo;
    List<CheckDoneList> checkList;
}
