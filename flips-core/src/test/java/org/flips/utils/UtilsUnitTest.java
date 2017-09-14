package org.flips.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class UtilsUnitTest {

    @Test(expected = InvocationTargetException.class)
    public void shouldThrowAssertionErrorGivenAnAttemptIsMadeToInstantiateUtils() throws Exception {
        Constructor<?> constructor = Utils.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldReturnTrueGivenAnEmptyArray(){
        boolean isEmpty = Utils.isEmpty(new Object[]{});
        assertEquals(true, isEmpty);
    }

    @Test
    public void shouldReturnFalseGivenANonEmptyArray(){
        boolean isEmpty = Utils.isEmpty(new Object[]{"sample"});
        assertEquals(false, isEmpty);
    }

    @Test
    public void shouldReturnTrueGivenEmptyString(){
        boolean isEmpty = Utils.isEmpty("");
        assertEquals(true, isEmpty);
    }

    @Test
    public void shouldReturnTrueGivenNonEmptyString(){
        boolean isEmpty = Utils.isEmpty("Sample");
        assertEquals(false, isEmpty);
    }

    @Test
    public void shouldReturnEmptyArrayGivenClassType(){
        Object emptyArray = Utils.emptyArray(String.class);
        assertTrue(emptyArray instanceof String[]);
        assertEquals(0, ((String[])emptyArray).length);
    }

    @Test
    public void shouldReturnAccessibleMethodGivenAPublicMethod() throws Exception {
        Method method = Utils.getAccessibleMethod(TestUtils.class.getMethod("greeting"));
        assertNotNull(method);
    }

    @Test
    public void shouldInvokeTargetMethod() throws Exception {
        Method method       = Utils.getAccessibleMethod(TestUtils.class.getMethod("greeting"));
        TestUtils testUtils = new TestUtils();

        Object result       = Utils.invokeMethod(method, testUtils);
        assertEquals("Hello", result);
    }
}

class TestUtils{
    public String greeting(){
        return "Hello";
    }
}