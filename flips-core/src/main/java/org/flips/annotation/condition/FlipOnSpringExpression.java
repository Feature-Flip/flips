package org.flips.annotation.condition;

import org.flips.condition.SpringExpressionFlipCondition;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FlipOnOff(value = SpringExpressionFlipCondition.class)
public @interface FlipOnSpringExpression {

    String expression();
}