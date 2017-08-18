package org.flips.annotation.strategy;

import org.flips.strategy.StraightThroughFlipStrategyCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FeatureFlipStrategy(value = StraightThroughFlipStrategyCondition.class)
public @interface NoConditionFlipStrategy {

    boolean enabled() default false;
}
