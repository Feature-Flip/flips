package org.flips.annotation.strategy;

import org.flips.strategy.DisabledFeatureFlipStrategyCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FeatureFlipStrategy(value = DisabledFeatureFlipStrategyCondition.class)
public @interface DisabledFeatureFlipStrategy {
}
