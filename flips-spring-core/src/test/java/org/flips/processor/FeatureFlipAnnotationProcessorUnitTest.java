package org.flips.processor;

import org.flips.annotation.FeatureFlip;
import org.flips.model.AnnotationMetaData;
import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipAnnotationMetaDataFactory;
import org.flips.fixture.TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnnotationUtils.class})
public class FeatureFlipAnnotationProcessorUnitTest {

    @InjectMocks
    private FeatureFlipAnnotationProcessor featureFlipAnnotationProcessor;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private FeatureContext featureContext;

    @Mock
    private FeatureFlipAnnotationMetaDataFactory featureFlipAnnotationMetaDataFactory;

    @Test
    public void shouldReturnAnnotationMetaDataGivenMethodContainingFeatureFlipAnnotation() {
        Method method                           = PowerMockito.mock(Method.class);
        FeatureFlip featureFlip                 = mock(FeatureFlip.class);
        Annotation[] annotations                = new Annotation[]{featureFlip};
        AnnotationMetaData annotationMetaData   = mock(AnnotationMetaData.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.getAnnotations(method)).thenReturn(annotations);
        when(featureFlipAnnotationMetaDataFactory.buildAnnotationMetaData(annotations)).thenReturn(annotationMetaData);
        when(annotationMetaData.isEmpty()).thenReturn(false);

        AnnotationMetaData annotationMetaDataForMethod = featureFlipAnnotationProcessor.getAnnotationMetaData(method);

        assertNotNull("AnnotationMetaData was null after invoking featureFlipAnnotationProcessor.getAnnotationMetaData", annotationMetaDataForMethod);
        verify(featureFlipAnnotationMetaDataFactory).buildAnnotationMetaData(annotations);
        verify(annotationMetaData).isEmpty();

        PowerMockito.verifyStatic();
        AnnotationUtils.getAnnotations(method);

        PowerMockito.verifyStatic(never());
        AnnotationUtils.getAnnotations(method.getDeclaringClass());
    }

    @Test
    public void shouldReturnAnnotationMetaDataAtClassLevelGivenMethodNotContainingFeatureFlipAnnotation() {
        Method method                               = PowerMockito.method(TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class, "enabledFeature");
        FeatureFlip featureFlip                     = mock(FeatureFlip.class);
        Annotation[] annotations                    = new Annotation[]{featureFlip};
        Annotation[] emptyAnnotations               = new Annotation[0];
        AnnotationMetaData annotationMetaData       = mock(AnnotationMetaData.class);
        AnnotationMetaData emptyAnnotationMetaData  = mock(AnnotationMetaData.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.getAnnotations(method)).thenReturn(emptyAnnotations);
        when(featureFlipAnnotationMetaDataFactory.buildAnnotationMetaData(emptyAnnotations)).thenReturn(emptyAnnotationMetaData);
        when(emptyAnnotationMetaData.isEmpty()).thenReturn(true);
        when(AnnotationUtils.getAnnotations(method.getDeclaringClass())).thenReturn(annotations);
        when(featureFlipAnnotationMetaDataFactory.buildAnnotationMetaData(annotations)).thenReturn(annotationMetaData);

        AnnotationMetaData annotationMetaDataForMethod = featureFlipAnnotationProcessor.getAnnotationMetaData(method);

        assertNotNull("AnnotationMetaData was null after invoking featureFlipAnnotationProcessor.getAnnotationMetaData", annotationMetaDataForMethod);
        verify(featureFlipAnnotationMetaDataFactory).buildAnnotationMetaData(annotations);
        verify(featureFlipAnnotationMetaDataFactory).buildAnnotationMetaData(emptyAnnotations);
        verify(annotationMetaData, never()).isEmpty();
        verify(emptyAnnotationMetaData).isEmpty();

        PowerMockito.verifyStatic();
        AnnotationUtils.getAnnotations(method);
        AnnotationUtils.getAnnotations(method.getDeclaringClass());
    }
}