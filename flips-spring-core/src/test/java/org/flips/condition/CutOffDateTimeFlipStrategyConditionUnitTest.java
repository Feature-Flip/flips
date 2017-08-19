package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.junit.Test;

import java.time.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CutOffDateTimeFlipStrategyConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingStrategyConditionGivenCurrentDateIsGreaterThanCutoffDate(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);
        String cutoffDateTime                                             = "2016-10-17";

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        CutOffDateTimeFlipStrategyCondition strategyCondition = new CutOffDateTimeFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(true, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("cutoffDateTime", "");
    }

    @Test
    public void shouldReturnTrueOnMatchingStrategyConditionGivenCurrentDateTimeIsGreaterThanCutoffDateTime(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);
        String cutoffDateTime                                             = "2016-10-17 12:23:11";

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        CutOffDateTimeFlipStrategyCondition strategyCondition = new CutOffDateTimeFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(true, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("cutoffDateTime", "");
    }

    @Test
    public void shouldReturnFalseOnMatchingStrategyConditionGivenCurrentDateIsLesserThanCutoffDate(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);
        String cutoffDateTime                                             = LocalDate.now(ZoneId.of("UTC")).plusDays(2).toString();

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        CutOffDateTimeFlipStrategyCondition strategyCondition = new CutOffDateTimeFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("cutoffDateTime", "");
    }

    @Test
    public void shouldReturnFalseOnMatchingStrategyConditionGivenCurrentDateTimeIsGreaterThanCutoffDateTime(){
        LocalDateTime futureTime                                          = LocalDateTime.now(ZoneId.of("UTC")).plusHours(1);
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);
        String cutoffDateTime                                             = futureTime.getYear() + "-" + futureTime.getMonthValue() + "-" + futureTime.getDayOfMonth() + " " + futureTime.getHour() + ":" + futureTime.getMinute() + ":" + futureTime.getSecond();

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        CutOffDateTimeFlipStrategyCondition strategyCondition = new CutOffDateTimeFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("cutoffDateTime", "");
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionGivenDateIsProvidedInUnsupportedFormat(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);
        String cutoffDateTime                                             = "2017-10-12 12/12/12";

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("cutoffDateTime", "")).thenReturn(cutoffDateTime);

        CutOffDateTimeFlipStrategyCondition strategyCondition = new CutOffDateTimeFlipStrategyCondition();
        strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);
    }
}