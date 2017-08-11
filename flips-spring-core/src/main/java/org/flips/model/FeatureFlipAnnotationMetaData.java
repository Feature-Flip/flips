package org.flips.model;

import org.flips.annotation.FeatureFlip;
import org.flips.utils.ValidationUtils;
import org.springframework.context.ApplicationContext;

class FeatureFlipAnnotationMetaData extends AnnotationMetaData {

    private FeatureFlip featureFlip;

    protected <T extends FeatureFlip> FeatureFlipAnnotationMetaData(ApplicationContext  applicationContext,
                                                                    FeatureContext      featureContext,
                                                                    T                   annotation) {
        super(applicationContext, featureContext);

        ValidationUtils.requireNonNull(annotation, "FeatureFlip annotation can not be NULL while constructing FeatureFlipAnnotationMetaData");
        this.featureFlip = annotation;
    }

    @Override
    public boolean evaluate() {
        return featureFlip.enabled();
    }

    @Override
    public boolean isEmpty() {
        return featureFlip == null;
    }
}