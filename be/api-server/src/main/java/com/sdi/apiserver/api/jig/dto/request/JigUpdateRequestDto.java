package com.sdi.apiserver.api.jig.dto.request;

import com.sdi.apiserver.util.CheckList;
import lombok.Value;

import java.util.List;

@Value
public class JigUpdateRequestDto {
    String model;
    List<CheckList> checkList;
}
