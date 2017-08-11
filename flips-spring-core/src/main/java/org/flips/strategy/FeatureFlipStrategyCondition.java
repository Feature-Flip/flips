package org.flips.strategy;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;

public interface FeatureFlipStrategyCondition {

    boolean evaluateCondition(FeatureContext featureContext, FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes);
}