package org.flips.fixture;

import org.flips.annotation.*;
import org.flips.annotation.condition.FlipOnDateTime;
import org.flips.annotation.condition.FlipOnEnvironmentProperty;
import org.flips.annotation.condition.FlipOnProfiles;
import org.flips.annotation.condition.FlipOff;

@Flips
public class TestClientFlipAnnotationsWithAnnotationsAtMethodLevel {

    @FlipOff
    public void disabledFeature(){

    }

    @FlipOnDateTime(cutoffDateTime = "2016-07-10")
    public void pastDateFeature(){
    }

    @FlipOnDateTime(cutoffDateTime = "3030-07-10")
    public void futureDateFeature(){
    }

    @FlipOnDateTime(cutoffDateTime = "2016-07-10")
    @FlipOnEnvironmentProperty(property = "feature.disabled")
    public void pastDateFeatureWithDisabledSpringProperty(){
    }

    @FlipOnDateTime(cutoffDateTime = "2016-07-10")
    @FlipOnEnvironmentProperty(property = "feature.enabled")
    public void pastDateFeatureWithEnabledSpringProperty(){
    }

    @FlipOnProfiles(activeProfiles = {"dev", "qa"})
    public void springProfilesFeature(){
    }

    public void noFeatureConditionsAppliedEnabledByDefault(){
    }

    @FlipOff
    public void featureWithSameMethodNameInDifferentClass(){
    }

    @FlipOnDateTime(cutoffDateTime = "2016-07-10")
    @FlipOnEnvironmentProperty(property = "feature.enabled")
    @FlipOff
    public void featureWithFlipOffAndConditionBasedAnnotations(){
    }
}