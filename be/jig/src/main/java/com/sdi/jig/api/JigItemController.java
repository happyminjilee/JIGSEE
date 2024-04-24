package com.sdi.jig.api;

import com.sdi.jig.application.JigItemService;
import com.sdi.jig.dto.request.JigItemAddRequestDto;
import com.sdi.jig.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jig-item")
@RequiredArgsConstructor
public class JigItemController {

    private final JigItemService jigItemService;

    // 재고추가
    @PostMapping
    Response<Void> add(@RequestBody JigItemAddRequestDto dto){
        jigItemService.add(dto.list());
        return Response.success();
    }
}
