package org.flips.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.store.FeatureFlipAnnotationMetaDataStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FeatureFlipAdviceUnitTest {

    @InjectMocks
    private FeatureFlipAdvice featureFlipAdvice;

    @Mock
    private FeatureFlipAnnotationMetaDataStore featureFlipAnnotationMetaDataStore;

    @Test
    public void shouldExecuteSuccessfullyGivenFeatureIsEnabled() throws Throwable {
        JoinPoint joinPoint         = mock(JoinPoint.class);
        MethodSignature signature   = mock(MethodSignature.class);
        Method method               = PowerMockito.mock(Method.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(featureFlipAnnotationMetaDataStore.isFeatureEnabled(method)).thenReturn(true);

        featureFlipAdvice.inspectFeatureFlip(joinPoint);

        verify(joinPoint).getSignature();
        verify(signature).getMethod();
        verify(featureFlipAnnotationMetaDataStore).isFeatureEnabled(method);
    }

    @Test(expected = FeatureNotEnabledException.class)
    public void shouldThrowFeatureNotEnabledExceptionGivenFeatureIsDisabled() throws Throwable {
        JoinPoint joinPoint         = mock(JoinPoint.class);
        MethodSignature signature   = mock(MethodSignature.class);
        Method method               = PowerMockito.mock(Method.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(featureFlipAnnotationMetaDataStore.isFeatureEnabled(method)).thenReturn(false);

        featureFlipAdvice.inspectFeatureFlip(joinPoint);

        verify(joinPoint).getSignature();
        verify(signature).getMethod();
        verify(featureFlipAnnotationMetaDataStore).isFeatureEnabled(method);
    }
}