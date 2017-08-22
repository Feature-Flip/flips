package org.flips.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class ValidationUtilsUnitTest {

    @Test(expected = InvocationTargetException.class)
    public void shouldThrowInvocationTargetExceptionGivenAnAttemptIsMadeToInstantiateValidationUtils() throws Exception {
        Constructor<?> constructor = ValidationUtils.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNullValue(){
        ValidationUtils.requireNonEmpty((String)null, "Expected argument can not be null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenEmptyValue(){
        ValidationUtils.requireNonEmpty("", "Expected argument can not be null");
    }

    @Test
    public void shouldReturnTheValueAsIsGivenNonEmptyValue(){
        String result = ValidationUtils.requireNonEmpty("test", "Expected argument can not be null");
        assertEquals(result, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNullArray(){
        ValidationUtils.requireNonEmpty((String[])null, "Expected argument can not be null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenEmptyArray(){
        ValidationUtils.requireNonEmpty(new String[]{}, "Expected argument can not be null");
    }

    @Test
    public void shouldReturnTheValueAsIsGivenNonEmptyArray(){
        String[] result = ValidationUtils.requireNonEmpty(new String[]{"non-empty"}, "Expected argument can not be null");
        assertEquals(result, new String[]{"non-empty"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNullObject(){
        ValidationUtils.requireNonNull(null, "Expected argument can not be null");
    }

    @Test
    public void shouldReturnTheObjectAsIsGivenNonEmptyObject(){
        Map<String, Objects> annotationAttributes = new HashMap<>();
        Object result = ValidationUtils.requireNonNull(annotationAttributes, "Expected argument can not be null");

        assertEquals(annotationAttributes, result);
    }
}