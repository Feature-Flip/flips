package org.flips.utils;

import org.flips.annotation.FeatureFlip;
import org.flips.annotation.strategy.FeatureFlipStrategy;
import org.flips.annotation.strategy.SpringProfileFlipStrategy;
import org.flips.fixture.TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({org.springframework.core.annotation.AnnotationUtils.class})
public class AnnotationUtilsUnitTest {

    @Test(expected = AssertionError.class)
    public void shouldThrowAssertionErrorGivenAnAttemptIsMadeToInstantiateAnnotationUtils() throws Exception {
        Constructor<?> constructor = AnnotationUtils.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldReturnFeatureFlipAnnotationGivenAnnotationAndClassOfAnnotation(){
        FeatureFlip featureFlip              = mock(FeatureFlip.class);
        FeatureFlip featureFlipAnnotation    = mock(FeatureFlip.class);

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotation(featureFlip, FeatureFlip.class)).thenReturn(featureFlipAnnotation);

        FeatureFlip found = AnnotationUtils.getAnnotation(featureFlip, FeatureFlip.class);
        assertNotNull(found);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotation(featureFlip, FeatureFlip.class);
    }

    @Test
    public void shouldReturnAnnotationByTypeGivenArrayOfAnnotations(){
        FeatureFlip featureFlip              = mock(FeatureFlip.class);
        FeatureFlip featureFlipAnnotation    = mock(FeatureFlip.class);
        Annotation[] annotations             = {featureFlip};

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotations[0], FeatureFlip.class)).thenReturn(featureFlipAnnotation);

        FeatureFlip found = AnnotationUtils.findAnnotationByTypeIfAny(annotations, FeatureFlip.class);
        assertNotNull(found);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotations[0], FeatureFlip.class);
    }

    @Test
    public void shouldReturnNullGivenAnnotationTypeIsNotPresentInArrayOfAnnotations(){
        FeatureFlip featureFlip              = mock(FeatureFlip.class);
        Annotation[] annotations             = {featureFlip};

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotations[0], FeatureFlip.class)).thenReturn(null);

        FeatureFlip found = AnnotationUtils.findAnnotationByTypeIfAny(annotations, FeatureFlip.class);
        assertNull(found);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotations[0], FeatureFlip.class);
    }

    @Test
    public void shouldReturnTrueAsFlipStrategyPresentGivenAnAnnotationWithMetaAnnotationAsFlipStrategy(){
        SpringProfileFlipStrategy flipStrategy = mock(SpringProfileFlipStrategy.class);
        Annotation annotation                  = flipStrategy;

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.isAnnotationMetaPresent(annotation.getClass(), FeatureFlipStrategy.class)).thenReturn(true);

        boolean isFlipStrategyDefined = AnnotationUtils.isMetaAnnotationDefined(annotation, FeatureFlipStrategy.class);
        assertEquals(true, isFlipStrategyDefined);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.isAnnotationMetaPresent(annotation.getClass(), FeatureFlipStrategy.class);
    }

    @Test
    public void shouldReturnFalseAsFlipStrategyPresentGivenAnAnnotationWithoutMetaAnnotationAsFlipStrategy(){
        Inherited inherited         = mock(Inherited.class);
        Annotation annotation       = inherited;

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);

        boolean isFlipStrategyDefined = AnnotationUtils.isMetaAnnotationDefined(annotation, FeatureFlipStrategy.class);
        assertEquals(false, isFlipStrategyDefined);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.isAnnotationMetaPresent(annotation.getClass(), FeatureFlipStrategy.class);
    }

    @Test
    public void shouldReturnAnnotationsOnMethodGivenMethod(){
        Method method               = PowerMockito.mock(Method.class);
        Annotation[] annotations    = {mock(FeatureFlip.class)};

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotations(method)).thenReturn(annotations);

        Annotation[] annotationsOnMethod = AnnotationUtils.getAnnotations(method);
        assertEquals(annotations, annotationsOnMethod);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotations(method);
    }

    @Test
    public void shouldReturnAnnotationsOnClassGivenClass(){
        Annotation[] annotations    = {mock(FeatureFlip.class)};
        Class clazz                 = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class;

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotations(any(Class.class))).thenReturn(annotations);

        Annotation[] annotationsOnMethod = AnnotationUtils.getAnnotations(clazz);
        assertEquals(annotations, annotationsOnMethod);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotations(clazz);
    }

    @Test
    public void shouldReturnFeatureFlipStrategyInputMetaDataGivenFeatureFlipAnnotation(){
        SpringProfileFlipStrategy flipStrategy      = mock(SpringProfileFlipStrategy.class);
        Annotation annotation                       = flipStrategy;
        Map<String, Object> annotationAttributes    = new HashMap<String, Object>() {{
            put("activeProfiles", new String[]{"dev"});
        }};

        FeatureFlipStrategyAnnotationAttributes expectedMetaData = new FeatureFlipStrategyAnnotationAttributes.Builder().addAll(annotationAttributes).build();

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes(annotation)).thenReturn(annotationAttributes);

        FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        assertEquals(expectedMetaData, featureFlipStrategyAnnotationAttributes);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes(annotation);
    }
}