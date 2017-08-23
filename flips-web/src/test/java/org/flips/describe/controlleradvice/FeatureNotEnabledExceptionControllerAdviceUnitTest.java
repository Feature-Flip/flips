package org.flips.describe.controlleradvice;

import org.flips.exception.FeatureNotEnabledException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FeatureNotEnabledExceptionControllerAdviceUnitTest {

    @InjectMocks
    private FeatureNotEnabledExceptionControllerAdvice featureNotEnabledExceptionControllerAdvice;

    @Test
    public void shouldHandleUserNotFoundExceptionGivenFeatureIsDisabled() throws NoSuchMethodException {
        Method method                           = this.getClass().getMethod("testClientMethod");
        FeatureNotEnabledErrorResponse response = featureNotEnabledExceptionControllerAdvice.handleFeatureNotEnabledException(new FeatureNotEnabledException("feature disabled",method));

        assertEquals("feature disabled"       , response.getErrorMessage());
        assertEquals("testClientMethod"       , response.getFeatureName());
        assertEquals("org.flips.describe.controlleradvice.FeatureNotEnabledExceptionControllerAdviceUnitTest", response.getClassName());
    }

    public void testClientMethod(){}
}