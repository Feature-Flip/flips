package org.flips.condition;

import org.apache.commons.lang3.ArrayUtils;
import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SpringProfileFlipStrategyConditionUnitTest {

    @Test
    public void shouldReturnTrueOnMatchingStrategyConditionGivenAnyActiveProfileIsPresentInExpectedProfiles(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String[] expectedActiveProfiles = {"dev", "qa"};
        String[] activeProfiles         = {"dev"};

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipStrategyCondition strategyCondition = new SpringProfileFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(true, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY);
        verify(featureContext).getActiveProfilesOrEmpty();
    }

    @Test
    public void shouldReturnFalseOnMatchingStrategyConditionGivenNoActiveProfileIsPresentInExpectedProfiles(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String[] expectedActiveProfiles = {"dev", "qa"};
        String[] activeProfiles         = {"uat"};

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipStrategyCondition strategyCondition = new SpringProfileFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY);
        verify(featureContext).getActiveProfilesOrEmpty();
    }

    @Test
    public void shouldReturnFalseOnMatchingStrategyConditionGivenActiveProfilesIsEmpty(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String[] expectedActiveProfiles = {"dev", "qa"};
        String[] activeProfiles         = {};

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipStrategyCondition strategyCondition = new SpringProfileFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(false, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY);
        verify(featureContext).getActiveProfilesOrEmpty();
    }

    @Test
    public void shouldReturnTrueOnMatchingStrategyConditionGivenAnyActiveProfileIsPresentInExpectedProfilesWithActiveProfilesMoreThanExpectedProfiles(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String[] expectedActiveProfiles = {"dev", "qa"};
        String[] activeProfiles         = {"uat", "dev", "qa"};

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipStrategyCondition strategyCondition = new SpringProfileFlipStrategyCondition();
        boolean result = strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);

        assertEquals(true, result);
        verify(featureFlipStrategyAnnotationAttributes).getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY);
        verify(featureContext).getActiveProfilesOrEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenExpectedProfilesIsNull(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String[] expectedActiveProfiles = null;
        String[] activeProfiles         = {"uat"};

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipStrategyCondition strategyCondition = new SpringProfileFlipStrategyCondition();
        strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenExpectedProfilesIsEmpty(){
        FeatureContext featureContext                                     = mock(FeatureContext.class);
        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = mock(FeatureFlipStrategyAnnotationAttributes.class);

        String[] expectedActiveProfiles = {};
        String[] activeProfiles         = {"uat"};

        when(featureFlipStrategyAnnotationAttributes.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY)).thenReturn(expectedActiveProfiles);
        when(featureContext.getActiveProfilesOrEmpty()).thenReturn(activeProfiles);

        SpringProfileFlipStrategyCondition strategyCondition = new SpringProfileFlipStrategyCondition();
        strategyCondition.evaluateCondition(featureContext, featureFlipStrategyAnnotationAttributes);
    }
}