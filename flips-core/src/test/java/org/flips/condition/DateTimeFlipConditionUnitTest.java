package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DateTimeFlipConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenCurrentDateIsGreaterThanCutoffDate(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "feature.dev.enabled.date";

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);
        when(featureContext.getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "")).thenReturn("2015-02-05T02:05:17+00:00");

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTimeProperty", "");
        verify(featureContext).getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "");
    }

    @Test
    public void shouldReturnFalseOnMatchingConditionGivenCurrentDateIsLesserThanCutoffDate(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "feature.dev.disabled.date";

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);
        when(featureContext.getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "")).thenReturn(ZonedDateTime.now().plusDays(2).toInstant().toString());

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTimeProperty", "");
        verify(featureContext).getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "");
    }

    @Test(expected = DateTimeParseException.class)
    public void shouldThrowRuntimeExceptionGivenDateTimeIsProvidedInUnsupportedFormat(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "feature.dev.enabled.date";

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);
        when(featureContext.getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "")).thenReturn("2014-01-02");

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNoPropertyContainingDateTimeIsProvided(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "";

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenPropertyContainingDateTimeIsBlank(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "feature.dev.enabled.date";

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);
        when(featureContext.getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "")).thenReturn("");

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }
}