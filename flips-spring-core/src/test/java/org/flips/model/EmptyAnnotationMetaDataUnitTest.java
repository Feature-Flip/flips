package org.flips.model;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EmptyAnnotationMetaDataUnitTest {

    @Test
    public void shouldReturnIsEmptyGivenAnEmptyAnnotationMetaData(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);

        EmptyAnnotationMetaData annotationMetaData = new EmptyAnnotationMetaData(applicationContext, featureContext);
        assertEquals(true, annotationMetaData.isEmpty());
    }

    @Test
    public void shouldEvaluateToTrueGivenAnEmptyAnnotationMetaData(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);

        EmptyAnnotationMetaData annotationMetaData = new EmptyAnnotationMetaData(applicationContext, featureContext);
        assertEquals(true, annotationMetaData.evaluate());
    }
}