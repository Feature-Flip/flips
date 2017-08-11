package org.flips.annotation.strategy;

import org.flips.strategy.SpringExpressionFlipStrategyCondition;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FeatureFlipStrategy(value = SpringExpressionFlipStrategyCondition.class)
@Inherited
public @interface SpringExpressionFlipStrategy {

    String expression();
}