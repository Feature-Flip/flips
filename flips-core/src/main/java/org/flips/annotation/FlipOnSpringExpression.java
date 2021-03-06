package org.flips.annotation;

import org.flips.condition.SpringExpressionFlipCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@FlipOnOff(value = SpringExpressionFlipCondition.class)
public @interface FlipOnSpringExpression {

    String expression();
}