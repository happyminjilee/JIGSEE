package com.sdi.watching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class WatchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatchingApplication.class, args);
    }
}
