package org.flips.annotation.strategy;

import org.flips.strategy.CutOffDateTimeFlipStrategyCondition;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FeatureFlipStrategy(value = CutOffDateTimeFlipStrategyCondition.class)
public @interface CutOffDateTimeFlipStrategy {

    String cutoffDateTime();
}