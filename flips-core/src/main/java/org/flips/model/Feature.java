package org.flips.model;

public class Feature {

    private String featureName;
    private String className;

    public Feature(String featureName, String className) {
        this.featureName = featureName;
        this.className = className;
    }

    public String getFeatureName(){
        return featureName;
    }

    public String getClassName(){
        return className;
    }
}
