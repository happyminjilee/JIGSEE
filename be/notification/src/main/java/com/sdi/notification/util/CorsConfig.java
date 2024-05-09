package com.sdi.notification.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Value("${security.refresh-token-prefix}")
    private String REFRESH_TOKEN;

    @Value("${security.cors-allowed-origins}")
    private String[] allowedOrigins;

    @Value("${security.max-age}")
    private int maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*")
                .allowedHeaders(HttpHeaders.ORIGIN, "X-Requested-With", HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT, HttpHeaders.AUTHORIZATION, REFRESH_TOKEN)
                .exposedHeaders(HttpHeaders.AUTHORIZATION, REFRESH_TOKEN)
                .allowCredentials(true)
                .maxAge(maxAge);
    }
}
