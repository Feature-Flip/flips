package org.flips.strategy;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NoConditionFlipStrategyConditionUnitTest {

    @Test
    public void shouldReturnTrueOnGivenEnabledAttributeWasSetToTrue(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("enabled", Boolean.FALSE)).thenReturn(true);

        StraightThroughFlipStrategyCondition condition = new StraightThroughFlipStrategyCondition();
        boolean result = condition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(true, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("enabled", Boolean.FALSE);
    }

    @Test
    public void shouldReturnFalseOnGivenEnabledAttributeWasNotSet(){
        FeatureContext featureContext                                                   = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("enabled", Boolean.FALSE)).thenReturn(false);

        StraightThroughFlipStrategyCondition condition = new StraightThroughFlipStrategyCondition();
        boolean result = condition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("enabled", Boolean.FALSE);
    }
}