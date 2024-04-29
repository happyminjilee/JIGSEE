package com.sdi.apiserver.api.wo.response;

import com.sdi.apiserver.util.CheckDoneList;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class WorkOrderDetailResponseDto {

    Long id;
    String status;
    String creator;
    String terminator;
    String model;
    String serialNo;
    LocalDateTime createAt;
    List<CheckDoneList> checkList;
}
