package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.flips.utils.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static org.flips.utils.DateTimeUtils.UTC;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateTimeUtils.class)
public class DateTimeFlipConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenCurrentDateIsGreaterThanCutoffDate(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "feature.dev.enabled.date";

        PowerMockito.mockStatic(DateTimeUtils.class);

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);
        when(featureContext.getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "")).thenReturn("2015-02-05T02:05:17+00:00");
        PowerMockito.when(DateTimeUtils.currentTime()).thenReturn(ZonedDateTime.now(UTC));

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTimeProperty", "");
        verify(featureContext).getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "");

        PowerMockito.verifyStatic();
        DateTimeUtils.currentTime();
    }

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenCurrentDateIsEqualToCutoffDate(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "feature.dev.enabled.date";

        PowerMockito.mockStatic(DateTimeUtils.class);

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);
        when(featureContext.getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "")).thenReturn("2015-02-05T02:05:17+00:00");
        PowerMockito.when(DateTimeUtils.currentTime()).thenReturn(OffsetDateTime.parse("2015-02-05T02:05:17+00:00").atZoneSameInstant(DateTimeUtils.UTC));

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTimeProperty", "");
        verify(featureContext).getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "");

        PowerMockito.verifyStatic();
        DateTimeUtils.currentTime();
    }

    @Test
    public void shouldReturnFalseOnMatchingConditionGivenCurrentDateIsLesserThanCutoffDate(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "feature.dev.disabled.date";

        PowerMockito.mockStatic(DateTimeUtils.class);

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);
        when(featureContext.getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "")).thenReturn(ZonedDateTime.now().plusDays(2).toInstant().toString());
        PowerMockito.when(DateTimeUtils.currentTime()).thenReturn(ZonedDateTime.now(UTC));

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTimeProperty", "");
        verify(featureContext).getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "");

        PowerMockito.verifyStatic();
        DateTimeUtils.currentTime();
    }

    @Test(expected = DateTimeParseException.class)
    public void shouldThrowRuntimeExceptionGivenDateTimeIsProvidedInUnsupportedFormat(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTimeProperty                     = "feature.dev.enabled.date";

        PowerMockito.mockStatic(DateTimeUtils.class);
        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "")).thenReturn(cutoffDateTimeProperty);
        when(featureContext.getPropertyValueOrDefault(cutoffDateTimeProperty, String.class, "")).thenReturn("2014-01-02");
        PowerMockito.when(DateTimeUtils.currentTime()).thenReturn(ZonedDateTime.now(UTC));

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