package com.sdi.apiserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

public class HandleFeignResponse {
    public static <T> Response<T> handleFeignResponse(feign.Response backResponse, HttpServletResponse httpServletResponse, Class<T> responseType) throws IOException {
        InputStream inputStream = backResponse.body().asInputStream();
        ObjectMapper mapper = new ObjectMapper();
        Response<T> response = mapper.readValue(inputStream, mapper.getTypeFactory().constructParametricType(Response.class, responseType));

        if(backResponse.status() != HttpStatus.OK.value()) {
            httpServletResponse.setStatus(backResponse.status());
            return response;
        }

        Map<String, Collection<String>> headers = backResponse.headers();
        String accessToken = headers.get("Authorization").iterator().next();
        String refreshToken = headers.get("RefreshToken").iterator().next();

        // 양쪽의 []를 제거하고 헤더에 추가
        HeaderUtils.addAccessToken(httpServletResponse, accessToken.replace("[", "").replace("]", ""));
        HeaderUtils.addRefreshToken(httpServletResponse, refreshToken.replace("[", "").replace("]", ""));

        return response;
    }
}
