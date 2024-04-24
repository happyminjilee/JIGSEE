package com.sdi.jig.api;

import com.sdi.jig.application.JigService;
import com.sdi.jig.dto.response.JigResponseDto;
import com.sdi.jig.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jig")
@RequiredArgsConstructor
public class JigController {

    private final JigService jigService;

    @GetMapping
    Response<JigResponseDto> findByModel(@RequestParam(name = "model") String model) {
        return Response.success(JigResponseDto.from(jigService.findByModel(model)));
    }
}
