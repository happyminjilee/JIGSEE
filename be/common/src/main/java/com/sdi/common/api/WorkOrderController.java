package com.sdi.common.api;

import com.sdi.common.util.CommonException;
import com.sdi.common.util.ErrorCode;
import com.sdi.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/common")
public class CommonController {

    @GetMapping("/success")
    Response<Void> success(){
        return Response.success();
    }

    @GetMapping("/success-body")
    Response<Map<String, String>> successBody(){
        Map<String, String> response = new HashMap<>();
        response.put("data", "데이터");
        return Response.success(response);
    }

    @GetMapping("/fail")
    void fail(){
        throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
