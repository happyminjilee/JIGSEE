package com.sdi.jig.api;

import com.sdi.jig.application.JigService;
import com.sdi.jig.dto.request.JigUpdateRequestDto;
import com.sdi.jig.dto.response.JigResponseDto;
import com.sdi.jig.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jig")
@RequiredArgsConstructor
public class JigController {

    private final JigService jigService;

    @PutMapping
    Response<Void> updateCheckList(@RequestBody JigUpdateRequestDto dto){
        jigService.updateCheckList(dto.model(), dto.checkList());
        return Response.success();
    }

    @GetMapping
    Response<JigResponseDto> findByModel(@RequestParam(name = "model") String model) {
        return Response.success(jigService.findByModel(model));
    }
}
