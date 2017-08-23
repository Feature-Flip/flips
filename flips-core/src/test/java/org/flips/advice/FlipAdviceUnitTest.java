package org.flips.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.store.FlipAnnotationsStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FlipAdviceUnitTest {

    @InjectMocks
    private FlipAdvice flipAdvice;

    @Mock
    private FlipAnnotationsStore flipAnnotationsStore;

    @Test
    public void shouldExecuteSuccessfullyGivenFeatureIsEnabled() throws Throwable {
        JoinPoint joinPoint         = mock(JoinPoint.class);
        MethodSignature signature   = mock(MethodSignature.class);
        Method method               = PowerMockito.mock(Method.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(flipAnnotationsStore.isFeatureEnabled(method)).thenReturn(true);

        flipAdvice.inspectFlips(joinPoint);

        verify(joinPoint).getSignature();
        verify(signature).getMethod();
        verify(flipAnnotationsStore).isFeatureEnabled(method);
    }

    @Test(expected = FeatureNotEnabledException.class)
    public void shouldThrowFeatureNotEnabledExceptionGivenFeatureIsDisabled() throws Throwable {
        JoinPoint joinPoint         = mock(JoinPoint.class);
        MethodSignature signature   = mock(MethodSignature.class);
        Method method               = this.getClass().getMethod("dummyMethod");

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(flipAnnotationsStore.isFeatureEnabled(method)).thenReturn(false);

        flipAdvice.inspectFlips(joinPoint);

        verify(joinPoint).getSignature();
        verify(signature).getMethod();
        verify(flipAnnotationsStore).isFeatureEnabled(method);
    }

    public void dummyMethod(){}

}