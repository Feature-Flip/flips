package org.flips.store;

import org.flips.annotation.Flips;
import org.flips.model.AnnotationMetaData;
import org.flips.model.FeatureFlipAnnotationMetaDataFactory;
import org.flips.processor.FeatureFlipAnnotationProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FeatureFlipAnnotationMetaDataStoreUnitTest {

    public static class FeatureFlipAnnotationTestClient {
        public  void method1(){
        }
        private void method2(){
        }
    }

    @InjectMocks
    private FeatureFlipAnnotationMetaDataStore  featureFlipAnnotationMetaDataStore;

    @Mock
    private ApplicationContext                  applicationContext;

    @Mock
    private FeatureFlipAnnotationProcessor      featureFlipAnnotationProcessor;

    @Mock
    private FeatureFlipAnnotationMetaDataFactory featureFlipAnnotationMetaDataFactory;

    @Test
    public void shouldNotStoreFeatureFlipAnnotationMetaDataGivenNoBeansWereAnnotatedWithFlipsAnnotation(){
        when(applicationContext.getBeansWithAnnotation(Flips.class)).thenReturn(Collections.EMPTY_MAP);

        featureFlipAnnotationMetaDataStore.buildFeatureFlipAnnotationMetaDataStore();

        assertEquals(0, featureFlipAnnotationMetaDataStore.getTotalMethodsCached());
        verify(applicationContext).getBeansWithAnnotation(Flips.class);
        verify(featureFlipAnnotationProcessor, never()).getAnnotationMetaData(any(Method.class));
    }

    @Test
    public void shouldNotStoreFeatureFlipAnnotationMetaDataGivenAnnotationMetaDataIsBlank() throws Exception {
        Map<String, Object> flipComponents = new HashMap<String, Object>(){{
            put("featureFlipAnnotationClient", new FeatureFlipAnnotationTestClient());
        }};

        Method method                               = FeatureFlipAnnotationTestClient.class.getMethod("method1");
        AnnotationMetaData emptyAnnotationMetaData  = mock(AnnotationMetaData.class);

        when(applicationContext.getBeansWithAnnotation(Flips.class)).thenReturn(flipComponents);
        when(featureFlipAnnotationProcessor.getAnnotationMetaData(method)).thenReturn(emptyAnnotationMetaData);
        when(emptyAnnotationMetaData.isEmpty()).thenReturn(true);

        featureFlipAnnotationMetaDataStore.buildFeatureFlipAnnotationMetaDataStore();

        assertEquals(0, featureFlipAnnotationMetaDataStore.getTotalMethodsCached());
        verify(applicationContext).getBeansWithAnnotation(Flips.class);
        verify(featureFlipAnnotationProcessor).getAnnotationMetaData(method);
        verify(emptyAnnotationMetaData).isEmpty();
    }

    @Test
    public void shouldStoreFeatureFlipAnnotationMetaDataGivenBeansWereAnnotatedWithFlipsAnnotationsAndMethodsWereAccessibleWithNonEmptyAnnotationMetaData() throws Exception {
        Map<String, Object> flipComponents = new HashMap<String, Object>(){{
            put("featureFlipAnnotationClient", new FeatureFlipAnnotationTestClient());
        }};

        Method method                          = FeatureFlipAnnotationTestClient.class.getMethod("method1");
        AnnotationMetaData annotationMetaData  = mock(AnnotationMetaData.class);

        when(applicationContext.getBeansWithAnnotation(Flips.class)).thenReturn(flipComponents);
        when(featureFlipAnnotationProcessor.getAnnotationMetaData(method)).thenReturn(annotationMetaData);
        when(annotationMetaData.isEmpty()).thenReturn(false);

        featureFlipAnnotationMetaDataStore.buildFeatureFlipAnnotationMetaDataStore();

        assertEquals(1, featureFlipAnnotationMetaDataStore.getTotalMethodsCached());
        verify(applicationContext).getBeansWithAnnotation(Flips.class);
        verify(featureFlipAnnotationProcessor).getAnnotationMetaData(method);
        verify(annotationMetaData).isEmpty();
    }

    @Test
    public void shouldReturnFeatureEnabledGivenAnnotationMetaDataReturnTrue() throws Exception{
        Method method                           = FeatureFlipAnnotationTestClient.class.getMethod("method1");
        AnnotationMetaData annotationMetaData   = mock(AnnotationMetaData.class);
        Map<Method, AnnotationMetaData> store   = new HashMap<Method, AnnotationMetaData>(){{
            put(method, annotationMetaData);
        }};

        ReflectionTestUtils.setField(featureFlipAnnotationMetaDataStore, "methodFeatureFlipAnnotationMetaDataStore", store);

        when(annotationMetaData.evaluate()).thenReturn(true);
        boolean featureEnabled = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, featureEnabled);
        verify(annotationMetaData).evaluate();
    }

    @Test
    public void shouldReturnFeatureDisabledGivenAnnotationMetaDataReturnFalse() throws Exception{
        Method method                           = FeatureFlipAnnotationTestClient.class.getMethod("method1");
        AnnotationMetaData annotationMetaData   = mock(AnnotationMetaData.class);
        Map<Method, AnnotationMetaData> store   = new HashMap<Method, AnnotationMetaData>(){{
            put(method, annotationMetaData);
        }};

        ReflectionTestUtils.setField(featureFlipAnnotationMetaDataStore, "methodFeatureFlipAnnotationMetaDataStore", store);

        when(annotationMetaData.evaluate()).thenReturn(false);
        boolean featureEnabled = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(false, featureEnabled);
        verify(annotationMetaData).evaluate();
    }

    @Test
    public void shouldReturnFeatureEnabledGivenMethodIsNotCached() throws Exception{
        Method method                           = FeatureFlipAnnotationTestClient.class.getMethod("method1");
        AnnotationMetaData annotationMetaData   = mock(AnnotationMetaData.class);
        Map<Method, AnnotationMetaData> store   = new HashMap<Method, AnnotationMetaData>(){{
            put(null, annotationMetaData);
        }};
        ReflectionTestUtils.setField(featureFlipAnnotationMetaDataStore, "methodFeatureFlipAnnotationMetaDataStore", store);

        when(featureFlipAnnotationMetaDataFactory.getEmptyAnnotationMetaData()).thenReturn(annotationMetaData);
        when(annotationMetaData.evaluate()).thenReturn(true);
        boolean featureEnabled = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, featureEnabled);
        verify(annotationMetaData).evaluate();
    }
}

