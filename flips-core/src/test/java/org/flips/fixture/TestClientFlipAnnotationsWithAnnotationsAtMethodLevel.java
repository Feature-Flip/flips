package org.flips.fixture;

import org.flips.annotation.condition.FlipOff;
import org.flips.annotation.condition.FlipOnDateTime;
import org.flips.annotation.condition.FlipOnEnvironmentProperty;
import org.flips.annotation.condition.FlipOnProfiles;
import org.springframework.stereotype.Component;

@Component
public class TestClientFlipAnnotationsWithAnnotationsAtMethodLevel {

    @FlipOff
    public void disabledFeature(){

    }

    @FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
    public void pastDateFeature(){
    }

    @FlipOnDateTime(cutoffDateTimeProperty = "future.feature.date")
    public void futureDateFeature(){
    }

    @FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
    @FlipOnEnvironmentProperty(property = "feature.disabled")
    public void pastDateFeatureWithDisabledSpringProperty(){
    }

    @FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
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

    @FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
    @FlipOnEnvironmentProperty(property = "feature.enabled")
    @FlipOff
    public void featureWithFlipOffAndConditionBasedAnnotations(){
    }
}