package com.sdi.jig.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class TimeCalculator {

    public static Long timeDiffToMills(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMillis();
    }

    public static String millsToString(Long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.SECONDS.toMillis(seconds);

        return String.format("%d일 %02d시간 %02d분 %02d초 %03d밀리초", days, hours, minutes, seconds, millis);
    }
}
