package com.sdi.apiserver.api.jig.dto.request;

import com.sdi.apiserver.util.CheckItem;
import lombok.Value;

import java.util.List;

@Value
public class JigUpdateRequestDto {
    String model;
    List<CheckItem> checkList;
}
