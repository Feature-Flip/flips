package org.flips.fixture;

import org.flips.annotation.*;
import org.flips.annotation.strategy.CutOffDateTimeFlipStrategy;
import org.flips.annotation.strategy.SpringEnvironmentPropertyFlipStrategy;
import org.flips.annotation.strategy.NoConditionFlipStrategy;

@Flips
@CutOffDateTimeFlipStrategy(cutoffDateTime = "2016-07-10")
@SpringEnvironmentPropertyFlipStrategy(property = "feature.enabled")
public class TestClientFeatureFlipAnnotationsWithAnnotationsAtClassLevel {

    public void featureWithAnnotationsAtClassLevel(){
    }

    @NoConditionFlipStrategy
    public void featureWithAnnotationOverridingAtMethodLevel(){
    }

    @NoConditionFlipStrategy(enabled = true)
    public void featureWithSameMethodNameInDifferentClass(){
    }
}