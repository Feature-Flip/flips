package org.flips.annotation.condition;

import org.flips.condition.FlipCondition;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlipOnOff {

    Class<? extends FlipCondition> value();
}