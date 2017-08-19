package org.flips.annotation.strategy;

import org.flips.condition.SpringProfileFlipStrategyCondition;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FeatureFlipStrategy(value = SpringProfileFlipStrategyCondition.class)
public @interface SpringProfileFlipStrategy {

    String[] activeProfiles();
}