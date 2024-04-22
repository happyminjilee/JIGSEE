package com.sdi.apiserver.api.jig.dto.request;

import com.sdi.apiserver.api.jig.dto.response.JigItemResponseDto;
import lombok.Value;

import java.util.List;

@Value
public class JigItemCreateRequestDto {
    List<JigItemResponseDto> list;
}
