package com.sdi.apiserver.api.jig.dto.response;

import com.sdi.apiserver.util.CheckList;
import lombok.Value;

import java.util.List;

@Value
public class JigResponseDto {
    String model;
    String expectLife;
    List<CheckList> checkList;
}
