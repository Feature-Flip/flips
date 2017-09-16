package org.flips.annotation;

import org.flips.condition.DayOfWeekFlipCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.DayOfWeek;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@FlipOnOff(value = DayOfWeekFlipCondition.class)
public @interface FlipOnDaysOfWeek {

    DayOfWeek[] daysOfWeek();
}