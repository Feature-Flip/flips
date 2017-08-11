package org.flips.annotation.strategy;

import org.flips.strategy.SpringProfileFlipStrategyCondition;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FeatureFlipStrategy(value = SpringProfileFlipStrategyCondition.class)
@Inherited
public @interface SpringProfileFlipStrategy {

    String[] activeProfiles();
}