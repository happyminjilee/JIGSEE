package com.sdi.apiserver.api.jig;

import com.sdi.apiserver.api.jig.dto.request.JigUpdateRequestDto;
import com.sdi.apiserver.api.jig.dto.response.JigResponseDto;
import com.sdi.apiserver.util.CheckList;
import com.sdi.apiserver.util.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/jig")
public class JigController {

    @PutMapping()
    Response<Void> update(@RequestBody JigUpdateRequestDto dto){
        return Response.success();
    }

    @GetMapping()
    Response<JigResponseDto> searchByModel(@RequestParam(name = "model") String model){
        JigResponseDto dto = new JigResponseDto(
                "testModel",
                "testExcpectLife",
                List.of(
                        new CheckList("test1", "test1"),
                        new CheckList("test2", "test2")
                )
        );
        return Response.success(dto);
    }
}
