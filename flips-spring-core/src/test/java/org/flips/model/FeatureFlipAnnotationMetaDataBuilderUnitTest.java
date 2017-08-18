package org.flips.model;

import org.flips.annotation.strategy.FeatureFlipStrategy;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnnotationUtils.class)
public class FeatureFlipAnnotationMetaDataBuilderUnitTest {

    @InjectMocks
    private FeatureFlipAnnotationMetaDataBuilder featureFlipAnnotationMetaDataBuilder;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private FeatureContext featureContext;

    @Before
    public void setUp(){
        featureFlipAnnotationMetaDataBuilder.buildEmptyAnnotationMetadata();
    }

    @Test
    public void shouldReturnEmptyAnnotationMetaDataGivenEmptyAnnotations(){
        FeatureFlipAnnotationMetaDataBuilder featureFlipAnnotationMetaDataBuilder = new FeatureFlipAnnotationMetaDataBuilder(applicationContext, featureContext);

        AnnotationMetaData annotationMetaData = featureFlipAnnotationMetaDataBuilder.buildAnnotationMetaData(new Annotation[0]);
        assertTrue(annotationMetaData instanceof EmptyAnnotationMetaData);
    }

    @Test
    public void shouldReturnFeatureFlipStrategyAnnotationMetaDataGivenAnnotationsNotContainingFeatureFlip(){
        Annotation[]  annotations = new Annotation[1];

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class)).thenReturn(false);

        AnnotationMetaData annotationMetaData = featureFlipAnnotationMetaDataBuilder.buildAnnotationMetaData(annotations);

        assertTrue(annotationMetaData instanceof FeatureFlipStrategyAnnotationMetaData);

        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class);
    }
}