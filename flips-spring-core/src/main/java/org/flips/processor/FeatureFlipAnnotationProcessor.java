package org.flips.processor;

import org.flips.model.AnnotationMetaData;
import org.flips.model.FeatureFlipAnnotationMetaDataFactory;
import org.flips.utils.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class FeatureFlipAnnotationProcessor {

    private static final Logger logger = LoggerFactory.getLogger(FeatureFlipAnnotationProcessor.class);

    private FeatureFlipAnnotationMetaDataFactory featureFlipAnnotationMetaDataFactory;

    @Autowired
    public FeatureFlipAnnotationProcessor(FeatureFlipAnnotationMetaDataFactory featureFlipAnnotationMetaDataFactory) {
        this.featureFlipAnnotationMetaDataFactory   = featureFlipAnnotationMetaDataFactory;
    }

    public AnnotationMetaData getAnnotationMetaData(Method method){
        AnnotationMetaData annotationMetaData = getFeatureAnnotationMetaDataOnMethod(method);

        if ( !annotationMetaData.isEmpty() )    return  annotationMetaData;
        return                                          getFeatureAnnotationMetaDataOnClass(method.getDeclaringClass());
    }

    private AnnotationMetaData getFeatureAnnotationMetaDataOnMethod(Method method) {
        logger.debug("Getting feature annotation metadata at Method level {}", method.getName());
        Annotation[] annotations = AnnotationUtils.getAnnotations(method);
        return buildFeatureFlipAnnotationMetaData(annotations);
    }

    private AnnotationMetaData getFeatureAnnotationMetaDataOnClass(Class<?> clazz) {
        logger.debug("Getting feature annotation metadata at class level {}", clazz.getName());
        Annotation[] annotations = AnnotationUtils.getAnnotations(clazz);
        return buildFeatureFlipAnnotationMetaData(annotations);
    }

    private AnnotationMetaData buildFeatureFlipAnnotationMetaData(Annotation[] annotations) {
        return featureFlipAnnotationMetaDataFactory.buildAnnotationMetaData(annotations);
    }
}