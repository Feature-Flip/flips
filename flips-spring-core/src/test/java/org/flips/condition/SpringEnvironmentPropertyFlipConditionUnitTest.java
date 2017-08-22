package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SpringEnvironmentPropertyFlipConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenExpectedValueOfPropertyMatchesPropertyValue(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        when(flipAnnotationAttributes.getAttributeValue("property", "")).thenReturn("feature.email.compose");
        when(flipAnnotationAttributes.getAttributeValue("expectedValue", "")).thenReturn("Y");
        when(featureContext.getPropertyValueOrDefault("feature.email.compose", String.class, "")).thenReturn("Y");

        SpringEnvironmentPropertyFlipCondition condition = new SpringEnvironmentPropertyFlipCondition();
        boolean result                                   = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("property", "");
        verify(flipAnnotationAttributes).getAttributeValue("expectedValue", "");
        verify(featureContext).getPropertyValueOrDefault("feature.email.compose", String.class, "");
    }

    @Test
    public void shouldReturnFalseOnMatchingConditionGivenExpectedValueOfPropertyDoesNotMatchPropertyValue(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        when(flipAnnotationAttributes.getAttributeValue("property", "")).thenReturn("feature.email.compose");
        when(flipAnnotationAttributes.getAttributeValue("expectedValue", "")).thenReturn("N");
        when(featureContext.getPropertyValueOrDefault("feature.email.compose", String.class, "")).thenReturn("Y");

        SpringEnvironmentPropertyFlipCondition condition = new SpringEnvironmentPropertyFlipCondition();
        boolean result                                   = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("property", "");
        verify(flipAnnotationAttributes).getAttributeValue("expectedValue", "");
        verify(featureContext).getPropertyValueOrDefault("feature.email.compose", String.class, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenEnvironmentPropertyIsBlank(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        when(flipAnnotationAttributes.getAttributeValue("property", "")).thenReturn("");
        when(flipAnnotationAttributes.getAttributeValue("expectedValue", "")).thenReturn("N");

        SpringEnvironmentPropertyFlipCondition condition = new SpringEnvironmentPropertyFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenExpectedValueOfPropertyIsBlank(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        when(flipAnnotationAttributes.getAttributeValue("property", "")).thenReturn("feature.email.compose");
        when(flipAnnotationAttributes.getAttributeValue("expectedValue", "")).thenReturn("");

        SpringEnvironmentPropertyFlipCondition condition = new SpringEnvironmentPropertyFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }
}