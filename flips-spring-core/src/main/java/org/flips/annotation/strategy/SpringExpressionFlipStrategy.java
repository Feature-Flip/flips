package org.flips.annotation.strategy;

import org.flips.condition.SpringExpressionFlipStrategyCondition;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FeatureFlipStrategy(value = SpringExpressionFlipStrategyCondition.class)
public @interface SpringExpressionFlipStrategy {

    String expression();
}