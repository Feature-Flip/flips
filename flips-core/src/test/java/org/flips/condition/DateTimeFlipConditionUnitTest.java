package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.junit.Test;

import java.time.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DateTimeFlipConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenCurrentDateIsGreaterThanCutoffDate(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTime                             = "2016-10-17";

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTime", "");
    }

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenCurrentDateTimeIsGreaterThanCutoffDateTime(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTime                             = "2016-10-17 12:23:11";

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTime", "");
    }

    @Test
    public void shouldReturnFalseOnMatchingConditionGivenCurrentDateIsLesserThanCutoffDate(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTime                             = LocalDate.now(ZoneId.of("UTC")).plusDays(2).toString();

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTime", "");
    }

    @Test
    public void shouldReturnFalseOnMatchingConditionGivenCurrentDateTimeIsGreaterThanCutoffDateTime(){
        LocalDateTime futureTime                          = LocalDateTime.now(ZoneId.of("UTC")).plusHours(1);
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTime                             = futureTime.getYear() + "-" + futureTime.getMonthValue() + "-" + futureTime.getDayOfMonth() + " " + futureTime.getHour() + ":" + futureTime.getMinute() + ":" + futureTime.getSecond();

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("cutoffDateTime", "");
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionGivenDateIsProvidedInUnsupportedFormat(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        String cutoffDateTime                             = "2017-10-12 12/12/12";

        when(flipAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        DateTimeFlipCondition condition = new DateTimeFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }
}