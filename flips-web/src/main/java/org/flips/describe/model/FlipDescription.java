package org.flips.describe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.flips.model.Feature;

import java.io.Serializable;

public class FlipDescription implements Serializable {

    private Feature feature;

    @JsonProperty("enabled")
    private boolean enabled;

    public FlipDescription(String methodName, String className, boolean enabled) {
        this.feature = new Feature(methodName, className);
        this.enabled = enabled;
    }

    @JsonProperty("feature")
    public String getMethodName(){
        return feature.getFeatureName();
    }

    @JsonProperty("class")
    public String getClassName(){
        return feature.getClassName();
    }
}