package com.sdi.jig.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeCalculator {

    public static Long timeDiffToMills(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMillis();
    }

    public static String millsToString(Long mills) {
        long hours = mills / (1000 * 60 * 60);
        long minutes = (mills % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((mills % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
        long millis = mills % 1000;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
    }
}
