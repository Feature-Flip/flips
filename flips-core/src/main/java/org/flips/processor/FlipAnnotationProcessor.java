package org.flips.processor;

import org.flips.model.FlipConditionEvaluator;
import org.flips.model.FlipConditionEvaluatorFactory;
import org.flips.utils.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class FlipAnnotationProcessor {

    private static final Logger logger = LoggerFactory.getLogger(FlipAnnotationProcessor.class);

    private FlipConditionEvaluatorFactory flipConditionEvaluatorFactory;

    @Autowired
    public FlipAnnotationProcessor(FlipConditionEvaluatorFactory flipConditionEvaluatorFactory) {
        this.flipConditionEvaluatorFactory = flipConditionEvaluatorFactory;
    }

    public FlipConditionEvaluator getFlipConditionEvaluator(Method method){
        return getFlipConditionEvaluatorOnMethod(method);
    }

    private FlipConditionEvaluator getFlipConditionEvaluatorOnMethod(Method method) {
        logger.debug("Getting feature condition evaluator at Method level {}", method.getName());

        Annotation[] annotations = AnnotationUtils.getAnnotations(method);
        return buildFlipConditionEvaluator(annotations);
    }

    private FlipConditionEvaluator buildFlipConditionEvaluator(Annotation[] annotations) {
        return flipConditionEvaluatorFactory.buildFlipConditionEvaluator(annotations);
    }
}