package org.flips.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.store.FlipAnnotationsStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class FlipAdvice {

    private FlipAnnotationsStore flipAnnotationsStore;

    @Autowired
    public FlipAdvice(@Lazy FlipAnnotationsStore flipAnnotationsStore) {
        this.flipAnnotationsStore = flipAnnotationsStore;
    }

    @Pointcut("execution(@(@org.flips.annotation.condition.FlipOnOff *) * *(..))")
    private void featureToInspectPointcut(){}

    @Before("featureToInspectPointcut()")
    public void inspectFlips(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature   = (MethodSignature) joinPoint.getSignature();
        Method method               = signature.getMethod();

        this.ensureFeatureIsEnabled(method);
    }

    private void ensureFeatureIsEnabled(Method method) {
        boolean featureEnabled = flipAnnotationsStore.isFeatureEnabled(method);
        if ( !featureEnabled )
            throw new FeatureNotEnabledException("Feature not enabled, identified by method " + method, method);
    }
}