package org.flips.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.flips.annotation.FlipBean;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.exception.FlipBeanFailedException;
import org.flips.store.FlipAnnotationsStore;
import org.flips.utils.AnnotationUtils;
import org.flips.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@Aspect
public class FlipBeanAdvice {

    private ApplicationContext applicationContext;

    private FlipAnnotationsStore flipAnnotationsStore;

    private static final Logger logger = LoggerFactory.getLogger(FlipBeanAdvice.class);

    @Autowired
    public FlipBeanAdvice(ApplicationContext applicationContext, @Lazy FlipAnnotationsStore flipAnnotationsStore) {
        this.applicationContext = applicationContext;
        this.flipAnnotationsStore = flipAnnotationsStore;
    }

    @Pointcut("@annotation(org.flips.annotation.FlipBean)")
    private void flipBeanPointcut(){}

    @Around("flipBeanPointcut()")
    public Object inspectFlips(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature   = (MethodSignature) joinPoint.getSignature();
        Method method               = signature.getMethod();
        FlipBean annotation         = AnnotationUtils.getAnnotation(method, FlipBean.class);
        Class<?> tobeFlippedWith    = annotation.with();

        if ( shouldFlipBean(method, tobeFlippedWith) ) {
            Method targetMethod = getMethodOnTargetBean(method, tobeFlippedWith);
            logger.info("Flipping {} of {} with {} of {}", method.getName(), method.getDeclaringClass().getName(), targetMethod.getName(), targetMethod.getDeclaringClass().getName());
            return invokeMethod(joinPoint, tobeFlippedWith, targetMethod);
        }

        return joinPoint.proceed();
    }

    private boolean shouldFlipBean(Method method, Class<?> tobeFlippedWith) {
        return  flipAnnotationsStore.isFeatureEnabled(method) &&
                (tobeFlippedWith != method.getDeclaringClass());
    }

    private Method getMethodOnTargetBean(Method method, Class<?> tobeFlippedWith) {
        try{
            return ClassUtils.getMethod(tobeFlippedWith, method.getName(), method.getParameterTypes());
        }
        catch (IllegalStateException e){
            throw new FlipBeanFailedException("Could not invoke " + method.getName() + " with parameters " + method.getParameterTypes() + " on class " + tobeFlippedWith, e);
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
                throw new FlipBeanFailedException(ex);
            }
        }
    }
}
