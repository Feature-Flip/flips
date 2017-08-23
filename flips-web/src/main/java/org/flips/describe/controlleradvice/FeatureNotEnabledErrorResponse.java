package org.flips.describe.controlleradvice;

import org.flips.exception.FeatureNotEnabledException;
import org.flips.model.Feature;

public class FeatureNotEnabledErrorResponse {

    private String  errorMessage;
    private Feature feature;

    public FeatureNotEnabledErrorResponse(FeatureNotEnabledException ex) {
        this.errorMessage = ex.getMessage();
        this.feature      = ex.getFeature();
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public String getFeatureName(){
        return feature.getFeatureName();
    }

    public String getClassName(){
        return feature.getClassName();
    }
}