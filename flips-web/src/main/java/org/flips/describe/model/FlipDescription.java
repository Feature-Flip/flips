package org.flips.describe.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FlipDescription implements Serializable {

    @JsonProperty("feature")
    private String  methodName;
    @JsonProperty("class")
    private String  className;
    @JsonProperty("enabled")
    private boolean isEnabled;

    public FlipDescription(String methodName, String className, boolean isEnabled) {
        this.methodName = methodName;
        this.className  = className;
        this.isEnabled  = isEnabled;
    }

    public String getMethodName(){
        return methodName;
    }
}