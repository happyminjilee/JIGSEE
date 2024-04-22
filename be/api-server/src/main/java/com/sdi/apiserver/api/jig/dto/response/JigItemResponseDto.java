package com.sdi.apiserver.api.jig.dto.response;

import com.sdi.apiserver.util.CheckList;
import com.sdi.apiserver.api.jig.dto.util.JigStatus;
import com.sdi.apiserver.util.CheckType;
import lombok.Value;

import java.util.List;

@Value
public class JigItemResponseDto {
    Long id;
    String model;
    String serialNo;
    JigStatus status;
    String expectLife;
    CheckType checkType;
    Integer repairCount;
    Integer checkCount;
    List<CheckList> checkList;

}
