package org.flips.exception;

import lombok.Getter;

import java.lang.reflect.Method;

public class FeatureNotEnabledException extends RuntimeException {
    @Getter
    private String featureName ;

    @Getter
    private String className;

    public FeatureNotEnabledException(String message, Method method) {
        super(message);
        featureName    = method.getName();
        className      = method.getDeclaringClass().getName();
    }}
