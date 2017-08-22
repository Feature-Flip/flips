package org.flips.model;

import org.springframework.context.ApplicationContext;

public abstract class FlipConditionEvaluator {

    protected final ApplicationContext  applicationContext;
    protected final FeatureContext      featureContext;

    protected FlipConditionEvaluator(ApplicationContext applicationContext, FeatureContext featureContext){
        this.applicationContext = applicationContext;
        this.featureContext     = featureContext;
    }

    public abstract boolean evaluate();
    public abstract boolean isEmpty();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlipConditionEvaluator{");
        sb.append(this.getClass().getName());
        sb.append('}');
        return sb.toString();
    }
}