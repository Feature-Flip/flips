package org.flips.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.flips.annotation.FlipBeanWith;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.exception.FlipWithBeanFailedException;
import org.flips.utils.AnnotationUtils;
import org.flips.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@Aspect
public class FlipBeanAdvice {

    private ApplicationContext applicationContext;

    @Autowired
    public FlipBeanAdvice(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Pointcut("within(@org.flips.annotation.FlipBeanWith *)")
    private void flipBeanPointcut(){}

    @Around("flipBeanPointcut()")
    public Object inspectFlips(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature   = (MethodSignature) joinPoint.getSignature();
        Method method               = signature.getMethod();
        FlipBeanWith annotation     = AnnotationUtils.findAnnotation(method.getDeclaringClass(), FlipBeanWith.class);
        Class<?> tobeFlippedWith    = annotation.alternateBean();

        if ( !isTargetSameAsDeclaringClassOfMethod(method, tobeFlippedWith) ){
            Method targetMethod = getMethodOnTargetBean(method, tobeFlippedWith);
            return invokeMethod(joinPoint, tobeFlippedWith, targetMethod);
        }

        return joinPoint.proceed();
    }

    private boolean isTargetSameAsDeclaringClassOfMethod(Method method, Class<?> tobeFlippedWith) {
        return tobeFlippedWith == method.getDeclaringClass();
    }

    private Method getMethodOnTargetBean(Method method, Class<?> tobeFlippedWith) {
        try{
            return ClassUtils.getMethod(tobeFlippedWith, method.getName(), method.getParameterTypes());
        }
        catch (IllegalStateException e){
            throw new FlipWithBeanFailedException("Could not invoke " + method.getName() + " alternateBean parameters " + method.getParameterTypes() + " on class " + tobeFlippedWith, e);
        }
    }

    private Object invokeMethod(ProceedingJoinPoint joinPoint, Class<?> tobeFlippedWith, Method targetMethod) throws Throwable {
        try{
            return Utils.invokeMethod(targetMethod, applicationContext.getBean(tobeFlippedWith), joinPoint.getArgs());
        }
        catch (InvocationTargetException ex){
            if ( ex.getCause() instanceof FeatureNotEnabledException ){
                throw ex.getCause();
            }else{
                throw new FlipWithBeanFailedException(ex);
            }
        }
    }
}
