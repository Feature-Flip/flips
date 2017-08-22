package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.springframework.stereotype.Component;

@Component
public class FlipOffCondition implements FlipCondition {

    @Override
    public boolean evaluateCondition(FeatureContext featureContext, FlipAnnotationAttributes flipAnnotationAttributes) {
        return false;
    }
}