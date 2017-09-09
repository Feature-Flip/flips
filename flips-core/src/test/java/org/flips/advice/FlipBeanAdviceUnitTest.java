package org.flips.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.flips.annotation.FlipBean;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.exception.FlipBeanFailedException;
import org.flips.fixture.TestClientFlipBeanSpringComponentSource;
import org.flips.fixture.TestClientFlipBeanSpringComponentTarget;
import org.flips.store.FlipAnnotationsStore;
import org.flips.utils.AnnotationUtils;
import org.flips.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnnotationUtils.class, Utils.class})
public class FlipBeanAdviceUnitTest {

    @InjectMocks
    private FlipBeanAdvice flipBeanAdvice;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private FlipAnnotationsStore flipAnnotationsStore;

    @Test
    public void shouldNotFlipBeanAsTargetClassToBeFlippedWithIsSameAsSourceClass() throws Throwable {
        ProceedingJoinPoint joinPoint         = mock(ProceedingJoinPoint.class);
        MethodSignature signature             = mock(MethodSignature.class);
        Method method                         = TestClientFlipBeanSpringComponentSource.class.getMethod("noFlip", String.class);
        FlipBean flipBean                     = mock(FlipBean.class);
        Class tobeFlippedWith                 = TestClientFlipBeanSpringComponentSource.class;

        PowerMockito.mockStatic(AnnotationUtils.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(AnnotationUtils.getAnnotation(method, FlipBean.class)).thenReturn(flipBean);
        when(flipBean.with()).thenReturn(tobeFlippedWith);
        when(flipAnnotationsStore.isFeatureEnabled(method)).thenReturn(true);

        flipBeanAdvice.inspectFlips(joinPoint);

        verify(joinPoint).getSignature();
        verify(signature).getMethod();
        PowerMockito.verifyStatic();
        AnnotationUtils.getAnnotation(method, FlipBean.class);

        verify(flipBean).with();
        verify(joinPoint).proceed();
        verify(applicationContext, never()).getBean(any(Class.class));
        verify(flipAnnotationsStore).isFeatureEnabled(method);
    }

    @Test
    public void shouldNotFlipBeanAsConditionToFlipBeanEvaluateToFalse() throws Throwable {
        ProceedingJoinPoint joinPoint         = mock(ProceedingJoinPoint.class);
        MethodSignature signature             = mock(MethodSignature.class);
        Method method                         = TestClientFlipBeanSpringComponentSource.class.getMethod("noFlip", String.class);
        FlipBean flipBean                     = mock(FlipBean.class);
        Class tobeFlippedWith                 = TestClientFlipBeanSpringComponentSource.class;

        PowerMockito.mockStatic(AnnotationUtils.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(AnnotationUtils.getAnnotation(method, FlipBean.class)).thenReturn(flipBean);
        when(flipBean.with()).thenReturn(tobeFlippedWith);
        when(flipAnnotationsStore.isFeatureEnabled(method)).thenReturn(false);

        flipBeanAdvice.inspectFlips(joinPoint);

        verify(joinPoint).getSignature();
        verify(signature).getMethod();
        PowerMockito.verifyStatic();
        AnnotationUtils.getAnnotation(method, FlipBean.class);

        verify(flipBean).with();
        verify(joinPoint).proceed();
        verify(applicationContext, never()).getBean(any(Class.class));
        verify(flipAnnotationsStore).isFeatureEnabled(method);
    }

    @Test
    public void shouldFlipBeanAsTargetClassToBeFlippedWithIsNotSameAsSourceClass() throws Throwable {
        ProceedingJoinPoint joinPoint         = mock(ProceedingJoinPoint.class);
        MethodSignature signature             = mock(MethodSignature.class);
        Method method                         = TestClientFlipBeanSpringComponentSource.class.getMethod("map", String.class);
        FlipBean flipBean                     = mock(FlipBean.class);
        Class tobeFlippedWith                 = TestClientFlipBeanSpringComponentTarget.class;

        PowerMockito.mockStatic(AnnotationUtils.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(joinPoint.getArgs()).thenReturn(new Object[]{"Input"});
        when(AnnotationUtils.getAnnotation(method, FlipBean.class)).thenReturn(flipBean);
        when(flipBean.with()).thenReturn(tobeFlippedWith);
        when(applicationContext.getBean(tobeFlippedWith)).thenReturn(mock(tobeFlippedWith));
        when(flipAnnotationsStore.isFeatureEnabled(method)).thenReturn(true);

        flipBeanAdvice.inspectFlips(joinPoint);

        verify(joinPoint).getSignature();
        verify(signature).getMethod();
        PowerMockito.verifyStatic();
        AnnotationUtils.getAnnotation(method, FlipBean.class);

        verify(flipBean).with();
        verify(applicationContext).getBean(tobeFlippedWith);
        verify(joinPoint).getArgs();
        verify(joinPoint, never()).proceed();
        verify(flipAnnotationsStore).isFeatureEnabled(method);
    }

    @Test(expected = FlipBeanFailedException.class)
    public void shouldNotFlipBeanGivenTargetClassDoesNotContainTheMethodToBeInvoked() throws Throwable {
        ProceedingJoinPoint joinPoint         = mock(ProceedingJoinPoint.class);
        MethodSignature signature             = mock(MethodSignature.class);
        Method method                         = TestClientFlipBeanSpringComponentSource.class.getMethod("nextDate");
        FlipBean flipBean                     = mock(FlipBean.class);
        Class tobeFlippedWith                 = TestClientFlipBeanSpringComponentTarget.class;

        PowerMockito.mockStatic(AnnotationUtils.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(joinPoint.getArgs()).thenReturn(new Object[]{"Input"});
        when(AnnotationUtils.getAnnotation(method, FlipBean.class)).thenReturn(flipBean);
        when(flipBean.with()).thenReturn(tobeFlippedWith);
        when(applicationContext.getBean(tobeFlippedWith)).thenReturn(mock(tobeFlippedWith));
        when(flipAnnotationsStore.isFeatureEnabled(method)).thenReturn(true);

        flipBeanAdvice.inspectFlips(joinPoint);
    }

    @Test(expected = FlipBeanFailedException.class)
    public void shouldNotFlipBeanGivenInvocationTargetExceptionIsThrownWhileInvokingMethodOnTargetBean() throws Throwable {
        ProceedingJoinPoint joinPoint         = mock(ProceedingJoinPoint.class);
        MethodSignature signature             = mock(MethodSignature.class);
        Method method                         = TestClientFlipBeanSpringComponentSource.class.getMethod("currentDate");
        FlipBean flipBean                     = mock(FlipBean.class);
        Class tobeFlippedWith                 = TestClientFlipBeanSpringComponentTarget.class;
        Object bean                           = mock(tobeFlippedWith);

        PowerMockito.mockStatic(AnnotationUtils.class);
        PowerMockito.mockStatic(Utils.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(joinPoint.getArgs()).thenReturn(new Object[]{"Input"});
        when(AnnotationUtils.getAnnotation(method, FlipBean.class)).thenReturn(flipBean);
        when(flipBean.with()).thenReturn(tobeFlippedWith);
        when(applicationContext.getBean(tobeFlippedWith)).thenReturn(bean);
        when(flipAnnotationsStore.isFeatureEnabled(method)).thenReturn(true);
        PowerMockito.doThrow(new InvocationTargetException(new RuntimeException("test"))).when(Utils.class, "invokeMethod", any(Method.class), any(), any(Object[].class));

        flipBeanAdvice.inspectFlips(joinPoint);
    }

    @Test(expected = FeatureNotEnabledException.class)
    public void shouldNotFlipBeanGivenFeatureNotEnabledExceptionIsThrownWhileInvokingMethodOnTargetBean() throws Throwable {
        ProceedingJoinPoint joinPoint         = mock(ProceedingJoinPoint.class);
        MethodSignature signature             = mock(MethodSignature.class);
        Method method                         = TestClientFlipBeanSpringComponentSource.class.getMethod("currentDate");
        FlipBean flipBean = mock(FlipBean.class);
        Class tobeFlippedWith                 = TestClientFlipBeanSpringComponentTarget.class;
        Object bean                           = mock(tobeFlippedWith);

        PowerMockito.mockStatic(AnnotationUtils.class);
        PowerMockito.mockStatic(Utils.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(joinPoint.getArgs()).thenReturn(new Object[]{"Input"});
        when(AnnotationUtils.getAnnotation(method, FlipBean.class)).thenReturn(flipBean);
        when(flipBean.with()).thenReturn(tobeFlippedWith);
        when(applicationContext.getBean(tobeFlippedWith)).thenReturn(bean);
        when(flipAnnotationsStore.isFeatureEnabled(method)).thenReturn(true);
        PowerMockito.doThrow(new InvocationTargetException(new FeatureNotEnabledException("feature not enabled", method))).when(Utils.class, "invokeMethod", any(Method.class), any(), any(Object[].class));

        flipBeanAdvice.inspectFlips(joinPoint);
    }
}