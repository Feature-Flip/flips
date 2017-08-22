package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FlipOffConditionUnitTest {

    @Test
    public void shouldReturnFalseGivenFeatureIsFlippedOff(){
        FeatureContext featureContext                     = mock(FeatureContext.class);
        FlipAnnotationAttributes flipAnnotationAttributes = mock(FlipAnnotationAttributes.class);

        FlipOffCondition condition = new FlipOffCondition();
        boolean result = condition.evaluateCondition(featureContext, flipAnnotationAttributes);

        assertEquals(false, result);
    }
}