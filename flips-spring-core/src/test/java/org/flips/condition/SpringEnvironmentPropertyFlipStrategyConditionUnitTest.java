package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SpringEnvironmentPropertyFlipStrategyConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingStrategyConditionGivenExpectedValueOfPropertyMatchesPropertyValue(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("property", "")).thenReturn("feature.email.compose");
        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expectedValue", "")).thenReturn("Y");
        when(featureContext.getPropertyValueOrDefault("feature.email.compose", String.class, "")).thenReturn("Y");

        SpringEnvironmentPropertyFlipStrategyCondition strategyCondition = new SpringEnvironmentPropertyFlipStrategyCondition();
        boolean result                                                   = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(true, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("property", "");
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("expectedValue", "");
        verify(featureContext).getPropertyValueOrDefault("feature.email.compose", String.class, "");
    }

    @Test
    public void shouldReturnFalseOnMatchingStrategyConditionGivenExpectedValueOfPropertyDoesNotMatchPropertyValue(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("property", "")).thenReturn("feature.email.compose");
        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expectedValue", "")).thenReturn("N");
        when(featureContext.getPropertyValueOrDefault("feature.email.compose", String.class, "")).thenReturn("Y");

        SpringEnvironmentPropertyFlipStrategyCondition strategyCondition = new SpringEnvironmentPropertyFlipStrategyCondition();
        boolean result                                                   = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("property", "");
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("expectedValue", "");
        verify(featureContext).getPropertyValueOrDefault("feature.email.compose", String.class, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenEnvironmentPropertyIsBlank(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("property", "")).thenReturn("");
        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expectedValue", "")).thenReturn("N");

        SpringEnvironmentPropertyFlipStrategyCondition strategyCondition = new SpringEnvironmentPropertyFlipStrategyCondition();
        strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenExpectedValueOfPropertyIsBlank(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("property", "")).thenReturn("feature.email.compose");
        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("expectedValue", "")).thenReturn("");

        SpringEnvironmentPropertyFlipStrategyCondition strategyCondition = new SpringEnvironmentPropertyFlipStrategyCondition();
        strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);
    }
}