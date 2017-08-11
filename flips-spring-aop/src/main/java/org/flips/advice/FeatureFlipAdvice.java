package org.flips.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.store.FeatureFlipAnnotationMetaDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class FeatureFlipAdvice {

    private FeatureFlipAnnotationMetaDataStore featureFlipAnnotationMetaDataStore;

    @Autowired
    public FeatureFlipAdvice(FeatureFlipAnnotationMetaDataStore featureFlipAnnotationMetaDataStore) {
        this.featureFlipAnnotationMetaDataStore = featureFlipAnnotationMetaDataStore;
    }

    @Pointcut("@within(org.flips.annotation.Flips)")
    private void featureEnabledPointcut(){}

    @Before("featureEnabledPointcut()")
    public void inspectFeatureFlip(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature   = (MethodSignature) joinPoint.getSignature();
        Method method               = signature.getMethod();

        this.ensureFeatureIsEnabled(method);
    }

    private void ensureFeatureIsEnabled(Method method) {
        boolean featureEnabled = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);
        if ( !featureEnabled )
            throw new FeatureNotEnabledException("Feature identified by method " + method);
    }
}