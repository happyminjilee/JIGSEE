package com.sdi.apiserver.api;

import com.sdi.apiserver.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CommonRequest {

    private final RestTemplate restTemplate;

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public <T> ResponseEntity<Response<T>> get(String url){
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    public <T, S> ResponseEntity<Response<T>> post(String url, S body){
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, headers()),
                new ParameterizedTypeReference<>() {}
        );
    }

    public <T, S> ResponseEntity<Response<T>> put(String url, S body){
        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(body, headers()),
                new ParameterizedTypeReference<>() {}
        );
    }

    public <T, S> ResponseEntity<Response<T>> delete(String url, S body){
        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                new HttpEntity<>(body, headers()),
                new ParameterizedTypeReference<>() {}
        );
    }
}
