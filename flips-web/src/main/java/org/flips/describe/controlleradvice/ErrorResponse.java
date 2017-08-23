package org.flips.describe.controlleradvice;

import lombok.Getter;
import org.flips.exception.FeatureNotEnabledException;

public class ErrorResponse {

    @Getter
    private String message;

    @Getter
    private String featureName ;

    @Getter
    private String className;

    public ErrorResponse(FeatureNotEnabledException exception, String message) {
        this.message   = message;
        className      = exception.getClassName();
        featureName    = exception.getFeatureName();
    }
}