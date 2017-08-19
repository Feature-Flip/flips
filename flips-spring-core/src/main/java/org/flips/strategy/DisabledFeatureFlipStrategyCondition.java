package org.flips.strategy;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.springframework.stereotype.Component;

@Component
public class DisabledFeatureFlipStrategyCondition implements FeatureFlipStrategyCondition {

    @Override
    public boolean evaluateCondition(FeatureContext featureContext, FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes) {
        return false;
    }
}