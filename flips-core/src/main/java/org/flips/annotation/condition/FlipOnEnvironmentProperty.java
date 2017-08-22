package org.flips.annotation.condition;

import org.flips.condition.SpringEnvironmentPropertyFlipCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FlipOnOff(value = SpringEnvironmentPropertyFlipCondition.class)
public @interface FlipOnEnvironmentProperty {

    String property();
    String expectedValue() default "true";
}