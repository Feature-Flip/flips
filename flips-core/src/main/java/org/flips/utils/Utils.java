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
        if (!isAccessible(method)) {
            return null;
        }
        final Class<?> cls = method.getDeclaringClass();
        if (Modifier.isPublic(cls.getModifiers())) {
            return method;
        }
        final String methodName = method.getName();
        final Class<?>[] parameterTypes = method.getParameterTypes();

        method = getAccessibleMethodFromInterfaceNest(cls, methodName, parameterTypes);

        if (method == null) {
            method = getAccessibleMethodFromSuperclass(cls, methodName, parameterTypes);
        }
        return method;
    }

    public static Object invokeMethod(Method method, Object obj, Object... args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(obj, args);
    }

    private static boolean isAccessible(Method method){
        return method != null && Modifier.isPublic(method.getModifiers()) && !method.isSynthetic();
    }

    private static Method getAccessibleMethodFromInterfaceNest(Class<?> cls, final String methodName, final Class<?>... parameterTypes) {
        for (; cls != null; cls = cls.getSuperclass()) {

            final Class<?>[] interfaces = cls.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (!Modifier.isPublic(anInterface.getModifiers())) {
                    continue;
                }
                try {
                    return anInterface.getDeclaredMethod(methodName,
                            parameterTypes);
                } catch (final NoSuchMethodException e) {
                }
                final Method method = getAccessibleMethodFromInterfaceNest(anInterface, methodName, parameterTypes);
                if (method != null) {
                    return method;
                }
            }
        }
        return null;
    }

    private static Method getAccessibleMethodFromSuperclass(final Class<?> cls, final String methodName, final Class<?>... parameterTypes) {
        Class<?> parentClass = cls.getSuperclass();
        while (parentClass != null) {
            if (Modifier.isPublic(parentClass.getModifiers())) {
                try {
                    return parentClass.getMethod(methodName, parameterTypes);
                } catch (final NoSuchMethodException e) {
                    return null;
                }
            }
            parentClass = parentClass.getSuperclass();
        }
        return null;
    }
}