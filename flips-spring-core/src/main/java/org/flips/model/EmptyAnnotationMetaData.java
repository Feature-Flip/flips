package org.flips.model;

import org.springframework.context.ApplicationContext;

class EmptyAnnotationMetaData extends AnnotationMetaData {

    protected EmptyAnnotationMetaData(ApplicationContext applicationContext, FeatureContext featureContext) {
        super(applicationContext, featureContext);
    }

    @Override
    public boolean evaluate() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}