package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;

public interface FlipCondition {

    boolean evaluateCondition(FeatureContext featureContext,
                              FlipAnnotationAttributes flipAnnotationAttributes);
}