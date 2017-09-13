package org.flips.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class Utils {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    private Utils() {
        throw new AssertionError("No Utils instances for you!");
    }

    public static boolean isEmpty(Object[] array){
        return ObjectUtils.isEmpty(array);
    }

    public static boolean isEmpty(String str){
        return StringUtils.isEmpty(str);
    }

    public static Object emptyArray(Class clazz){
        return Array.newInstance(clazz, 0);
    }

    public static Method getAccessibleMethod(Method method) {
        if ( Modifier.isPublic(method.getModifiers()) ) return method;
        else return null;
    }

    public static Object invokeMethod(Method method, Object obj, Object... args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(obj, args);
    }
}