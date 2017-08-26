package org.flips.utils;

import org.flips.annotation.FlipBean;
import org.flips.annotation.FlipOff;
import org.flips.annotation.FlipOnOff;
import org.flips.annotation.FlipOnProfiles;
import org.flips.fixture.TestClientFlipAnnotationsWithAnnotationsAtMethodLevel;
import org.flips.fixture.TestClientFlipBeanSpringComponentSource;
import org.flips.model.FlipAnnotationAttributes;
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
    public void shouldReturnFlipConditionAnnotationGivenAnnotationAndClassOfAnnotation(){
        FlipOff flipOff              = mock(FlipOff.class);
        FlipOff flipOffAnnotation    = mock(FlipOff.class);

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotation(flipOff, FlipOff.class)).thenReturn(flipOffAnnotation);

        FlipOff found = AnnotationUtils.getAnnotationOfType(flipOff, FlipOff.class);
        assertNotNull(found);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotation(flipOff, FlipOff.class);
    }

    @Test
    public void shouldReturnAnnotationByTypeGivenArrayOfAnnotations(){
        FlipOff flipOff              = mock(FlipOff.class);
        FlipOff flipOffAnnotation    = mock(FlipOff.class);
        Annotation[] annotations     = {flipOff};

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotations[0], FlipOff.class)).thenReturn(flipOffAnnotation);

        FlipOff found = AnnotationUtils.findAnnotationByTypeIfAny(annotations, FlipOff.class);
        assertNotNull(found);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotations[0], FlipOff.class);
    }

    @Test
    public void shouldReturnNullGivenAnnotationTypeIsNotPresentInArrayOfAnnotations(){
        FlipOff flipOff              = mock(FlipOff.class);
        Annotation[] annotations     = {flipOff};

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotations[0], FlipOff.class)).thenReturn(null);

        FlipOff found = AnnotationUtils.findAnnotationByTypeIfAny(annotations, FlipOff.class);
        assertNull(found);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotations[0], FlipOff.class);
    }

    @Test
    public void shouldReturnTrueAsFlipConditionPresentGivenAnAnnotationWithMetaAnnotationAsFlipOnOff(){
        FlipOnProfiles flipOnProfiles = mock(FlipOnProfiles.class);
        Annotation annotation         = flipOnProfiles;

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.isAnnotationMetaPresent(annotation.getClass(), FlipOnOff.class)).thenReturn(true);

        boolean isFlipConditionDefined = AnnotationUtils.isMetaAnnotationDefined(annotation, FlipOnOff.class);
        assertEquals(true, isFlipConditionDefined);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.isAnnotationMetaPresent(annotation.getClass(), FlipOnOff.class);
    }

    @Test
    public void shouldReturnFalseAsFlipConditionPresentGivenAnAnnotationWithoutMetaAnnotationAsFlipOnOff(){
        Inherited inherited         = mock(Inherited.class);
        Annotation annotation       = inherited;

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);

        boolean isFlipConditionDefined = AnnotationUtils.isMetaAnnotationDefined(annotation, FlipOnOff.class);
        assertEquals(false, isFlipConditionDefined);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.isAnnotationMetaPresent(annotation.getClass(), FlipOnOff.class);
    }

    @Test
    public void shouldReturnAnnotationsOnMethodGivenMethod(){
        Method method               = PowerMockito.mock(Method.class);
        Annotation[] annotations    = {mock(FlipOff.class)};

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotations(method)).thenReturn(annotations);

        Annotation[] annotationsOnMethod = AnnotationUtils.getAnnotations(method);
        assertEquals(annotations, annotationsOnMethod);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotations(method);
    }

    @Test
    public void shouldReturnAnnotationsOnClassGivenClass(){
        Annotation[] annotations    = {mock(FlipOff.class)};
        Class clazz                 = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class;

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotations(any(Class.class))).thenReturn(annotations);

        Annotation[] annotationsOnMethod = AnnotationUtils.getAnnotations(clazz);
        assertEquals(annotations, annotationsOnMethod);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotations(clazz);
    }

    @Test
    public void shouldReturnFlipAnnotationAttributesGivenFlipAnnotation(){
        FlipOnProfiles flipOnProfiles      = mock(FlipOnProfiles.class);
        Annotation annotation              = flipOnProfiles;

        Map<String, Object> annotationAttributes    = new HashMap<String, Object>() {{
            put("activeProfiles", new String[]{"dev"});
        }};

        FlipAnnotationAttributes expectedAnnotationAttributes = new FlipAnnotationAttributes.Builder().addAll(annotationAttributes).build();

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes(annotation)).thenReturn(annotationAttributes);

        FlipAnnotationAttributes flipAnnotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        assertEquals(expectedAnnotationAttributes, flipAnnotationAttributes);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes(annotation);
    }

    @Test
    public void shouldReturnAnnotationOfTypeOnClassGivenClass(){
        Class clazz                 = TestClientFlipBeanSpringComponentSource.class;
        FlipBean annotation         = mock(FlipBean.class);

        PowerMockito.mockStatic(org.springframework.core.annotation.AnnotationUtils.class);
        when(org.springframework.core.annotation.AnnotationUtils.findAnnotation(clazz, FlipBean.class)).thenReturn(annotation);

        FlipBean flipBean = AnnotationUtils.findAnnotation(clazz, FlipBean.class);
        assertEquals(annotation, flipBean);

        PowerMockito.verifyStatic();
        org.springframework.core.annotation.AnnotationUtils.findAnnotation(clazz, FlipBean.class);
    }
}