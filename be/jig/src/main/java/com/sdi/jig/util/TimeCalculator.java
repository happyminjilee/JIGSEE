package com.sdi.jig.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeCalculator {

    public static Long timeDiffToMills(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMillis();
    }

    public static String millsToString(Long mills) {
        long days = mills / (1000 * 60 * 60 * 24);
        long hours = (mills % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mills % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((mills % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
        long millis = mills % 1000;

        return String.format("%dD %02d:%02d:%02d.%03d", days, hours, minutes, seconds, millis);
    }
}
