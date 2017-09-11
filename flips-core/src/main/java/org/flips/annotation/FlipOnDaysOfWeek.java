package org.flips.annotation;

import java.time.DayOfWeek;

public @interface FlipOnDaysOfWeek {

    DayOfWeek[] daysOfWeek();
}