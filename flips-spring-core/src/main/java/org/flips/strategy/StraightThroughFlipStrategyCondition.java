package org.flips.strategy;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.springframework.stereotype.Component;

@Component
public class StraightThroughFlipStrategyCondition implements FeatureFlipStrategyCondition {

    @Override
    public boolean evaluateCondition(FeatureContext featureContext, FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes) {
        return featureFlipStrategyAnnotationAttributes.getAttributeValue("enabled", Boolean.FALSE);
    }
}