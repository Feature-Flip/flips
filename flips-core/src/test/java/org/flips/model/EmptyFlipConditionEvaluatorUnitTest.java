package org.flips.model;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EmptyFlipConditionEvaluatorUnitTest {

    @Test
    public void shouldReturnIsEmptyTrueGivenEmptyFlipConditionEvaluator(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);

        EmptyFlipConditionEvaluator conditionEvaluator = new EmptyFlipConditionEvaluator(applicationContext, featureContext);
        assertEquals(true, conditionEvaluator.isEmpty());
    }

    @Test
    public void shouldEvaluateToTrueGivenEmptyFlipConditionEvaluator(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);

        EmptyFlipConditionEvaluator conditionEvaluator = new EmptyFlipConditionEvaluator(applicationContext, featureContext);
        assertEquals(true, conditionEvaluator.evaluate());
    }
}