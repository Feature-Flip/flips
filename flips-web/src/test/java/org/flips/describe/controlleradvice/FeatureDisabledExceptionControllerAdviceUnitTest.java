package org.flips.describe.controlleradvice;

import org.flips.exception.FeatureNotEnabledException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FeatureDisabledExceptionControllerAdviceUnitTest {

    @InjectMocks
    private FeatureDisabledExceptionControllerAdvice featureDisabledExceptionControllerAdvice;

    @Test
    public void shouldHandleUserNotFoundExceptionGivenFeatureIsDisabled() throws NoSuchMethodException {
        Method method = this.getClass().getMethod("dummyMethod");

        ErrorResponse errorResponse = featureDisabledExceptionControllerAdvice.handleUserNotFoundException(new FeatureNotEnabledException("feature disabled",method));

        assertEquals("Feature not implemented", errorResponse.getMessage());
        assertEquals("dummyMethod"            , errorResponse.getFeatureName());
        assertEquals("org.flips.describe.controlleradvice.FeatureDisabledExceptionControllerAdviceUnitTest", errorResponse.getClassName());
    }

    public void dummyMethod(){}
}