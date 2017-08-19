package org.flips.fixture;

import org.flips.annotation.*;
import org.flips.annotation.strategy.CutOffDateTimeFlipStrategy;
import org.flips.annotation.strategy.SpringEnvironmentPropertyFlipStrategy;
import org.flips.annotation.strategy.SpringProfileFlipStrategy;
import org.flips.annotation.strategy.DisabledFeatureFlipStrategy;

@Flips
public class TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel {

    @DisabledFeatureFlipStrategy
    public void disabledFeature(){

    }

    @CutOffDateTimeFlipStrategy(cutoffDateTime = "2016-07-10")
    public void pastDateFeature(){
    }

    @CutOffDateTimeFlipStrategy(cutoffDateTime = "3030-07-10")
    public void futureDateFeature(){
    }

    @CutOffDateTimeFlipStrategy(cutoffDateTime = "2016-07-10")
    @SpringEnvironmentPropertyFlipStrategy(property = "feature.disabled")
    public void pastDateFeatureWithDisabledSpringProperty(){
    }

    @CutOffDateTimeFlipStrategy(cutoffDateTime = "2016-07-10")
    @SpringEnvironmentPropertyFlipStrategy(property = "feature.enabled")
    public void pastDateFeatureWithEnabledSpringProperty(){
    }

    @SpringProfileFlipStrategy(activeProfiles = {"dev", "qa"})
    public void springProfilesFeature(){
    }

    public void noFeatureConditionsAppliedEnabledByDefault(){
    }

    @DisabledFeatureFlipStrategy
    public void featureWithSameMethodNameInDifferentClass(){
    }

    @CutOffDateTimeFlipStrategy(cutoffDateTime = "2016-07-10")
    @SpringEnvironmentPropertyFlipStrategy(property = "feature.enabled")
    @DisabledFeatureFlipStrategy
    public void featureWithFeatureFlipAndStrategyAnnotations(){
    }
}