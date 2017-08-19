package org.flips.model;

import org.flips.annotation.strategy.FeatureFlipStrategy;
import org.flips.condition.FeatureFlipStrategyCondition;
import org.flips.utils.AnnotationUtils;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

class FeatureFlipStrategyAnnotationMetaData extends AnnotationMetaData {

    private static final Logger logger = LoggerFactory.getLogger(FeatureFlipStrategyAnnotationMetaData.class);

    private  Map<Class<? extends FeatureFlipStrategyCondition>, FeatureFlipStrategyAnnotationAttributes> strategyInputMetaData = new LinkedHashMap<>();

    protected FeatureFlipStrategyAnnotationMetaData(ApplicationContext applicationContext, FeatureContext featureContext, Annotation[] annotations) {
        super(applicationContext, featureContext);

        ValidationUtils.requireNonNull(annotations, "annotations[] can not be null while constructing FeatureFlipStrategyAnnotationMetaData");
        buildStrategyInputMetaData(annotations);
    }

    private void buildStrategyInputMetaData(Annotation[] annotations) {
        for ( Annotation annotation : annotations ){
            if ( AnnotationUtils.isMetaAnnotationDefined(annotation, FeatureFlipStrategy.class) ) {
                Class<? extends FeatureFlipStrategyCondition> featureFlipStrategyCondition = AnnotationUtils.getAnnotation(annotation, FeatureFlipStrategy.class).value();
                FeatureFlipStrategyAnnotationAttributes annotationAttributes               = AnnotationUtils.getAnnotationAttributes(annotation);

                strategyInputMetaData.put(featureFlipStrategyCondition, annotationAttributes);
            }
        }
        logger.debug("Built FeatureFlipStrategyAnnotationMetaData {}", strategyInputMetaData);
    }

    private boolean evaluateFeatureFlipCondition(ApplicationContext applicationContext,
                                                 FeatureContext     featureContext,
                                                 Class<? extends FeatureFlipStrategyCondition> conditionClazz,
                                                 FeatureFlipStrategyAnnotationAttributes inputMetaData) {

        return applicationContext.getBean(conditionClazz).evaluateCondition(featureContext, inputMetaData);
    }

    @Override
    public boolean evaluate() {
        return strategyInputMetaData
                .entrySet()
                .stream()
                .map(entry     -> evaluateFeatureFlipCondition(applicationContext, featureContext, entry.getKey(), entry.getValue()))
                .filter(result -> result == Boolean.FALSE)
                .findFirst().orElse(true);
    }

    @Override
    public boolean isEmpty() {
        return strategyInputMetaData.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeatureFlipStrategyAnnotationMetaData{");
        sb.append("strategyInputMetaData=").append(strategyInputMetaData);
        sb.append('}');
        return sb.toString();
    }
}