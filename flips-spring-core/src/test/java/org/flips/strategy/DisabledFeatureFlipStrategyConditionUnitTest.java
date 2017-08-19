package org.flips.strategy;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DisabledFeatureFlipStrategyConditionUnitTest {

    @Test
    public void shouldReturnFalseGivenDisabledFeatureFlipStrategyIsUsed(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        DisabledFeatureFlipStrategyCondition condition = new DisabledFeatureFlipStrategyCondition();
        boolean result = condition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
    }
}