package org.flips.utils;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateTimeUtils {

    public static final ZoneId UTC  = ZoneId.of("UTC");

    private DateTimeUtils() {
        throw new AssertionError("No DateTimeUtils instances for you!");
    }

    public static ZonedDateTime getCurrentTime(){
        return ZonedDateTime.now(UTC);
    }

    public static DayOfWeek getDayOfWeek(){
        return getCurrentTime().getDayOfWeek();
    }
}