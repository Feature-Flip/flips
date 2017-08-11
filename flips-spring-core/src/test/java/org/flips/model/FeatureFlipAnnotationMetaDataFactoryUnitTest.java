package org.flips.model;

import org.flips.annotation.FeatureFlip;
import org.flips.utils.AnnotationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnnotationUtils.class)
public class FeatureFlipAnnotationMetaDataFactoryUnitTest {

    @InjectMocks
    private FeatureFlipAnnotationMetaDataFactory featureFlipAnnotationMetaDataFactory;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private FeatureContext featureContext;

    @Before
    public void setUp(){
        featureFlipAnnotationMetaDataFactory.buildEmptyAnnotationMetadata();
    }

    @Test
    public void shouldReturnEmptyAnnotationMetaDataGivenEmptyAnnotations(){
        FeatureFlipAnnotationMetaDataFactory featureFlipAnnotationMetaDataFactory = new FeatureFlipAnnotationMetaDataFactory(applicationContext, featureContext);

        AnnotationMetaData annotationMetaData = featureFlipAnnotationMetaDataFactory.buildAnnotationMetaData(new Annotation[0]);
        assertTrue(annotationMetaData instanceof EmptyAnnotationMetaData);
    }

    @Test
    public void shouldReturnFeatureFlipAnnotationMetaDataGivenAnnotationsContainingFeatureFlip(){
        Annotation[] annotations = new Annotation[1];

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.findAnnotationByTypeIfAny(annotations, FeatureFlip.class)).thenReturn(mock(FeatureFlip.class));

        AnnotationMetaData annotationMetaData = featureFlipAnnotationMetaDataFactory.buildAnnotationMetaData(annotations);

        assertTrue(annotationMetaData instanceof FeatureFlipAnnotationMetaData);

        PowerMockito.verifyStatic();
        AnnotationUtils.findAnnotationByTypeIfAny(annotations, FeatureFlip.class);
    }

    @Test
    public void shouldReturnFeatureFlipStrategyAnnotationMetaDataGivenAnnotationsNotContainingFeatureFlip(){
        Annotation[]  annotations = new Annotation[1];

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.findAnnotationByTypeIfAny(annotations, FeatureFlip.class)).thenReturn(null);

        AnnotationMetaData annotationMetaData = featureFlipAnnotationMetaDataFactory.buildAnnotationMetaData(annotations);

        assertTrue(annotationMetaData instanceof FeatureFlipStrategyAnnotationMetaData);

        PowerMockito.verifyStatic();
        AnnotationUtils.findAnnotationByTypeIfAny(annotations, FeatureFlip.class);
    }
}