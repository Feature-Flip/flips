package org.flips.annotation.condition;

import org.flips.condition.DateTimeFlipCondition;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FlipOnOff(value = DateTimeFlipCondition.class)
public @interface FlipOnDateTime {

    String cutoffDateTimeProperty();
}