package org.flips.model;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.stereotype.Component;

@Component
public class FeatureContext {

    private Environment environment;
    private FeatureExpressionContext featureExpressionContext;

    private static final Logger logger = LoggerFactory.getLogger(FeatureContext.class);

    @Autowired
    public FeatureContext(Environment environment, FeatureExpressionContext featureExpressionContext) {
        this.environment              = environment;
        this.featureExpressionContext = featureExpressionContext;
    }

    public <T> T getPropertyValueOrDefault(String property, Class<T> t, T defaultValue) {
        logger.debug("Getting String property {}", property);
        return environment.getProperty(property, t, defaultValue);
    }

    public String[] getActiveProfilesOrEmpty(){
        String[] activeProfiles = environment.getActiveProfiles();
        logger.debug("Getting active profiles {}", activeProfiles);

        return ArrayUtils.isEmpty(activeProfiles) ? ArrayUtils.EMPTY_STRING_ARRAY : activeProfiles;
    }

    public ExpressionParser getExpressionParser(){
        return featureExpressionContext.getExpressionParser();
    }

    public EvaluationContext getEvaluationContext(){
        return featureExpressionContext.getEvaluationContext();
    }
}