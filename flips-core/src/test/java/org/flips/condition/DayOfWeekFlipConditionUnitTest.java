package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.flips.utils.DateTimeUtils;
import org.flips.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.DayOfWeek;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateTimeUtils.class)
public class DayOfWeekFlipConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenCurrentDayOfWeekIsAmongstSpecifiedDaysOfWeek(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        DayOfWeek[] empty                                 = (DayOfWeek[])Utils.emptyArray(DayOfWeek.class);

        PowerMockito.mockStatic(DateTimeUtils.class);

        when(flipAnnotationAttributes.getAttributeValue("daysOfWeek", empty)).thenReturn(new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY});
        PowerMockito.when(DateTimeUtils.getDayOfWeek()).thenReturn(DayOfWeek.MONDAY);

        DayOfWeekFlipCondition condition = new DayOfWeekFlipCondition();
        boolean result                   = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("daysOfWeek", empty);

        PowerMockito.verifyStatic();
        DateTimeUtils.getDayOfWeek();
    }

    @Test
    public void shouldReturnFalseOnMatchingConditionGivenCurrentDayOfWeekIsNotAmongstSpecifiedDaysOfWeek(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        DayOfWeek[] empty                                 = (DayOfWeek[])Utils.emptyArray(DayOfWeek.class);

        PowerMockito.mockStatic(DateTimeUtils.class);

        when(flipAnnotationAttributes.getAttributeValue("daysOfWeek", empty)).thenReturn(new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY});
        PowerMockito.when(DateTimeUtils.getDayOfWeek()).thenReturn(DayOfWeek.FRIDAY);

        DayOfWeekFlipCondition condition = new DayOfWeekFlipCondition();
        boolean result                   = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("daysOfWeek", empty);

        PowerMockito.verifyStatic();
        DateTimeUtils.getDayOfWeek();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenEmptyWeekDays(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        DayOfWeek[] empty                                 = (DayOfWeek[])Utils.emptyArray(DayOfWeek.class);

        PowerMockito.mockStatic(DateTimeUtils.class);

        when(flipAnnotationAttributes.getAttributeValue("daysOfWeek", empty)).thenReturn(new DayOfWeek[]{});
        PowerMockito.when(DateTimeUtils.getDayOfWeek()).thenReturn(DayOfWeek.FRIDAY);

        DayOfWeekFlipCondition condition = new DayOfWeekFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNullWeekDays(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);
        DayOfWeek[] empty                                 = (DayOfWeek[])Utils.emptyArray(DayOfWeek.class);

        PowerMockito.mockStatic(DateTimeUtils.class);

        when(flipAnnotationAttributes.getAttributeValue("daysOfWeek", empty)).thenReturn(null);
        PowerMockito.when(DateTimeUtils.getDayOfWeek()).thenReturn(DayOfWeek.FRIDAY);

        DayOfWeekFlipCondition condition = new DayOfWeekFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }
}