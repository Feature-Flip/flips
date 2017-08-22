package org.flips.processor;

import org.flips.annotation.condition.FlipOff;
import org.flips.fixture.TestClientFlipAnnotationsWithAnnotationsAtMethodLevel;
import org.flips.model.FlipConditionEvaluator;
import org.flips.model.FeatureContext;
import org.flips.model.FlipConditionEvaluatorFactory;
import org.flips.utils.AnnotationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnnotationUtils.class})
public class FlipAnnotationProcessorUnitTest {

    @InjectMocks
    private FlipAnnotationProcessor flipAnnotationProcessor;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private FeatureContext featureContext;

    @Mock
    private FlipConditionEvaluatorFactory flipConditionEvaluatorFactory;

    @Test
    public void shouldReturnFlipConditionEvaluatorGivenMethodWithFlipAnnotations() {
        Method method                                   = PowerMockito.mock(Method.class);
        FlipOff flipOff                                 = mock(FlipOff.class);
        Annotation[] annotations                        = new Annotation[]{flipOff};
        FlipConditionEvaluator flipConditionEvaluator   = mock(FlipConditionEvaluator.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.getAnnotations(method)).thenReturn(annotations);
        when(flipConditionEvaluatorFactory.buildFlipConditionEvaluator(annotations)).thenReturn(flipConditionEvaluator);
        when(flipConditionEvaluator.isEmpty()).thenReturn(false);

        FlipConditionEvaluator conditionEvaluator = flipAnnotationProcessor.getFlipConditionEvaluator(method);

        assertNotNull("FlipConditionEvaluator was null after invoking flipAnnotationProcessor.getFlipConditionEvaluator", conditionEvaluator);
        verify(flipConditionEvaluatorFactory).buildFlipConditionEvaluator(annotations);
        verify(flipConditionEvaluator).isEmpty();

        PowerMockito.verifyStatic();
        AnnotationUtils.getAnnotations(method);

        PowerMockito.verifyStatic(never());
        AnnotationUtils.getAnnotations(method.getDeclaringClass());
    }

    @Test
    public void shouldReturnFlipConditionEvaluatorAtClassLevelGivenMethodNotContainingFlipAnnotations() {
        Method method                               = PowerMockito.method(TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class, "disabledFeature");
        FlipOff flipOff                             = mock(FlipOff.class);
        Annotation[] annotations                    = new Annotation[]{flipOff};
        Annotation[] emptyAnnotations               = new Annotation[0];

        FlipConditionEvaluator flipConditionEvaluator = mock(FlipConditionEvaluator.class);
        FlipConditionEvaluator emptyFlipConditionEvaluator = mock(FlipConditionEvaluator.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.getAnnotations(method)).thenReturn(emptyAnnotations);
        when(flipConditionEvaluatorFactory.buildFlipConditionEvaluator(emptyAnnotations)).thenReturn(emptyFlipConditionEvaluator);
        when(emptyFlipConditionEvaluator.isEmpty()).thenReturn(true);
        when(AnnotationUtils.getAnnotations(method.getDeclaringClass())).thenReturn(annotations);
        when(flipConditionEvaluatorFactory.buildFlipConditionEvaluator(annotations)).thenReturn(flipConditionEvaluator);

        FlipConditionEvaluator conditionEvaluator = flipAnnotationProcessor.getFlipConditionEvaluator(method);

        assertNotNull("FlipConditionEvaluator was null after invoking flipAnnotationProcessor.getFlipConditionEvaluator", conditionEvaluator);
        verify(flipConditionEvaluatorFactory).buildFlipConditionEvaluator(annotations);
        verify(flipConditionEvaluatorFactory).buildFlipConditionEvaluator(emptyAnnotations);
        verify(flipConditionEvaluator, never()).isEmpty();
        verify(emptyFlipConditionEvaluator).isEmpty();

        PowerMockito.verifyStatic();
        AnnotationUtils.getAnnotations(method);
        AnnotationUtils.getAnnotations(method.getDeclaringClass());
    }
}