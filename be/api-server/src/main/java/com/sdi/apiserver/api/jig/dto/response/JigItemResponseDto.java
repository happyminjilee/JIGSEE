package com.sdi.apiserver.api.jig.dto.response;

import com.sdi.apiserver.util.CheckList;
import com.sdi.apiserver.api.jig.dto.util.JigStatus;
import lombok.Value;

import java.util.List;

@Value
public class JigItemResponseDto {
    Long id;
    String model;
    String serialNo;
    String status;
    String expectLife;
    Integer useCount;
    String useAccumulationTime;
    Integer repairCount;
    List<CheckList> checkList;
}
