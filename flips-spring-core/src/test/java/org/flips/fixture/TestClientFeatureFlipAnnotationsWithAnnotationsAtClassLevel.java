package org.flips.fixture;

import org.flips.annotation.*;
import org.flips.annotation.strategy.CutOffDateTimeFlipStrategy;
import org.flips.annotation.strategy.SpringEnvironmentPropertyFlipStrategy;
import org.flips.annotation.strategy.DisabledFeatureFlipStrategy;

@Flips
@CutOffDateTimeFlipStrategy(cutoffDateTime = "2016-07-10")
@SpringEnvironmentPropertyFlipStrategy(property = "feature.enabled")
public class TestClientFeatureFlipAnnotationsWithAnnotationsAtClassLevel {

    public void featureWithAnnotationsAtClassLevel(){
    }

    @DisabledFeatureFlipStrategy
    public void featureWithAnnotationOverridingAtMethodLevel(){
    }

    public void featureWithSameMethodNameInDifferentClass(){
    }
}