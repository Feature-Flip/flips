package org.flips.model;

import org.flips.annotation.FeatureFlip;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeatureFlipAnnotationMetaDataUnitTest {

    @Test
    public void shouldReturnTrueOnEvaluationGivenFeatureFlipIsSetToTrue(){
        FeatureFlip featureFlip = mock(FeatureFlip.class);
        FeatureFlipAnnotationMetaData annotationMetaData = new FeatureFlipAnnotationMetaData(mock(ApplicationContext.class), mock(FeatureContext.class), featureFlip);

        when(featureFlip.enabled()).thenReturn(true);
        boolean result = annotationMetaData.evaluate();

        assertEquals(true, result);
        verify(featureFlip).enabled();
    }

    @Test
    public void shouldReturnFalseOnEvaluationGivenFeatureFlipIsSetToFalse(){
        FeatureFlip featureFlip = mock(FeatureFlip.class);
        FeatureFlipAnnotationMetaData annotationMetaData = new FeatureFlipAnnotationMetaData(mock(ApplicationContext.class), mock(FeatureContext.class), featureFlip);

        when(featureFlip.enabled()).thenReturn(false);
        boolean result = annotationMetaData.evaluate();

        assertEquals(false, result);
        verify(featureFlip).enabled();
    }

    @Test
    public void shouldReturnFalseGivenFeatureFlipAnnotationMetaDataIsNonEmpty(){
        FeatureFlip featureFlip = mock(FeatureFlip.class);
        FeatureFlipAnnotationMetaData annotationMetaData = new FeatureFlipAnnotationMetaData(mock(ApplicationContext.class), mock(FeatureContext.class), featureFlip);

        boolean result = annotationMetaData.isEmpty();

        assertEquals(false, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenAnnotationIsNullWhileConstructingFeatureFlipAnnotationMetaData(){
        new FeatureFlipAnnotationMetaData(mock(ApplicationContext.class), mock(FeatureContext.class), null);
    }
}