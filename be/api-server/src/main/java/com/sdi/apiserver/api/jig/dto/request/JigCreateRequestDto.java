package com.sdi.apiserver.api.jig.dto.request;

import com.sdi.apiserver.util.CheckList;
import com.sdi.apiserver.util.CheckType;
import lombok.Value;

import java.util.List;

@Value
public class JigCreateRequestDto {
    String model;
    CheckType checkType;
    String expectLife;
    List<CheckList> checkList;
}
