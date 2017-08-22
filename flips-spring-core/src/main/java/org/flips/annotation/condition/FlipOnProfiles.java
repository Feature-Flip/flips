package org.flips.annotation.condition;

import org.flips.condition.SpringProfileFlipCondition;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FlipOnOff(value = SpringProfileFlipCondition.class)
public @interface FlipOnProfiles {

    String[] activeProfiles();
}