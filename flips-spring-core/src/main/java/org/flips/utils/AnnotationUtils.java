package org.flips.utils;

import org.flips.model.FlipAnnotationAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AnnotationUtils {

    public AnnotationUtils() {
        throw new AssertionError("No AnnotationUtils instances for you!");
    }

    public static  <T extends Annotation> T getAnnotation(Annotation annotation, Class<T> cls) {
        return org.springframework.core.annotation.AnnotationUtils.getAnnotation(annotation, cls);
    }

    public static  <T extends Annotation> T findAnnotationByTypeIfAny(Annotation[] annotations, Class<T> cls){
        for ( Annotation a : annotations ){
            T annotation = getAnnotation(a, cls);
            if ( annotation != null ) return annotation;
        }
        return null;
    }

    public static boolean isMetaAnnotationDefined(Annotation annotation, Class<? extends Annotation> annotationType) {
        return org.springframework.core.annotation.AnnotationUtils.isAnnotationMetaPresent(annotation.getClass(), annotationType);
    }

    public static Annotation[] getAnnotations(Method method){
        return org.springframework.core.annotation.AnnotationUtils.getAnnotations(method);
    }

    public static Annotation[] getAnnotations(Class<?> clazz){
        return org.springframework.core.annotation.AnnotationUtils.getAnnotations(clazz);
    }

    public static FlipAnnotationAttributes getAnnotationAttributes(Annotation annotation) {
        return new FlipAnnotationAttributes.Builder().addAll(org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes(annotation)).build();
    }
}