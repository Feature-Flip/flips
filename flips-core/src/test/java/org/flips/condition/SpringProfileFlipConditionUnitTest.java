package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.flips.utils.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SpringProfileFlipConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenAnyActiveProfileIsPresentInExpectedProfiles(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String[] expectedActiveProfiles = {"dev", "qa"};
        String[] activeProfiles         = {"dev"};

        when(flipAnnotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipCondition condition = new SpringProfileFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY);
        verify(featureContext).getActiveProfilesOrEmpty();
    }

    @Test
    public void shouldReturnFalseOnMatchingConditionGivenNoActiveProfileIsPresentInExpectedProfiles(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String[] expectedActiveProfiles = {"dev", "qa"};
        String[] activeProfiles         = {"uat"};

        when(flipAnnotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipCondition condition = new SpringProfileFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY);
        verify(featureContext).getActiveProfilesOrEmpty();
    }

    @Test
    public void shouldReturnFalseOnMatchingConditionGivenActiveProfilesIsEmpty(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String[] expectedActiveProfiles = {"dev", "qa"};
        String[] activeProfiles         = {};

        when(flipAnnotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipCondition condition = new SpringProfileFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
        verify(flipAnnotationAttributes).getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY);
        verify(featureContext).getActiveProfilesOrEmpty();
    }

    @Test
    public void shouldReturnTrueOnMatchingConditionGivenAnyActiveProfileIsPresentInExpectedProfilesWithActiveProfilesMoreThanExpectedProfiles(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String[] expectedActiveProfiles = {"dev", "qa"};
        String[] activeProfiles         = {"uat", "dev", "qa"};

        when(flipAnnotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipCondition condition = new SpringProfileFlipCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(true, result);
        verify(flipAnnotationAttributes).getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY);
        verify(featureContext).getActiveProfilesOrEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenExpectedProfilesIsNull(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String[] expectedActiveProfiles = null;
        String[] activeProfiles         = {"uat"};

        when(flipAnnotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipCondition condition = new SpringProfileFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenExpectedProfilesIsEmpty(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        String[] expectedActiveProfiles = {};
        String[] activeProfiles         = {"uat"};

        when(flipAnnotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipCondition condition = new SpringProfileFlipCondition();
        condition.evaluateCondition(featureContext, flipAnnotationAttributes);
    }
}