package com.sdi.jig.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TimeCalculator {

    public Long timeDiffToMills(LocalDateTime start, LocalDateTime end){
        return Duration.between(start, end).toMillis();
    }

    public String millsToString(Long mills){
        long hours = mills / (1000 * 60 * 60);
        long minutes = (mills % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((mills % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
        long millis = mills % 1000;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
    }
}
