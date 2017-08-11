package org.flips.model;

import org.springframework.context.ApplicationContext;

public abstract class AnnotationMetaData {

    protected ApplicationContext  applicationContext;
    protected FeatureContext      featureContext;

    protected AnnotationMetaData(ApplicationContext applicationContext, FeatureContext featureContext){
        this.applicationContext = applicationContext;
        this.featureContext     = featureContext;
    }

    public abstract boolean evaluate();
    public abstract boolean isEmpty();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnnotationMetaData{");
        sb.append(this.getClass().getName());
        sb.append('}');
        return sb.toString();
    }
}