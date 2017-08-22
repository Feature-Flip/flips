package org.flips.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.Arrays;

@Component
public class FlipConditionEvaluatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(FlipConditionEvaluatorFactory.class);
    private static FlipConditionEvaluator emptyFlipConditionEvaluator;

    private FeatureContext              featureContext;
    private ApplicationContext          applicationContext;

    @Autowired
    public FlipConditionEvaluatorFactory(ApplicationContext applicationContext, FeatureContext featureContext) {
        this.applicationContext = applicationContext;
        this.featureContext     = featureContext;
    }

    @PostConstruct
    protected void buildEmptyFlipConditionEvaluator(){
        emptyFlipConditionEvaluator = new EmptyFlipConditionEvaluator(applicationContext, featureContext);
    }

    public FlipConditionEvaluator buildFlipConditionEvaluator(Annotation[] annotations){
        logger.debug("Using FlipConditionEvaluatorFactory to build condition Evaluator for {}", Arrays.toString(annotations));
        if ( annotations.length == 0 ) return emptyFlipConditionEvaluator;
        return                         new DefaultFlipConditionEvaluator(applicationContext, featureContext, annotations);
    }

    public FlipConditionEvaluator getEmptyFlipConditionEvaluator(){
        return emptyFlipConditionEvaluator;
    }
}