package com.sdi.jig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class JigApplication {

    public static void main(String[] args) {
        SpringApplication.run(JigApplication.class, args);
    }
}
