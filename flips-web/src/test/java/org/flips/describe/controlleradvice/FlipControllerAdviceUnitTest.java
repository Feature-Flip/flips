package org.flips.describe.controlleradvice;

import org.flips.describe.model.FeatureNotEnabledErrorResponse;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.exception.FlipBeanFailedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FlipControllerAdviceUnitTest {

    @InjectMocks
    private FlipControllerAdvice flipControllerAdvice;

    @Test
    public void shouldHandleFeatureNotEnabledExceptionGivenFeatureIsDisabled() throws NoSuchMethodException {
        Method method                           = this.getClass().getMethod("testClientMethod");
        FeatureNotEnabledErrorResponse response = flipControllerAdvice.handleFeatureNotEnabledException(new FeatureNotEnabledException("feature disabled",method));

        assertEquals("feature disabled"       , response.getErrorMessage());
        assertEquals("testClientMethod"       , response.getFeatureName());
        assertEquals("org.flips.describe.controlleradvice.FlipControllerAdviceUnitTest", response.getClassName());
    }

    @Test
    public void shouldHandleFlipBeanFailedExceptionGivenFipWithBeanFailed() throws NoSuchMethodException {
        String response = flipControllerAdvice.handleFlipBeanFailedException(new FlipBeanFailedException("test"));
        assertEquals("test", response);
    }

    public void testClientMethod(){}
}