package org.flips.fixture;

import org.flips.annotation.*;
import org.flips.annotation.condition.FlipOnDateTime;
import org.flips.annotation.condition.FlipOnEnvironmentProperty;
import org.flips.annotation.condition.FlipOff;

@Flips
@FlipOnDateTime(cutoffDateTime = "2016-07-10")
@FlipOnEnvironmentProperty(property = "feature.enabled")
public class TestClientFlipAnnotationsWithAnnotationsAtClassLevel {

    public void featureWithAnnotationsAtClassLevel(){
    }

    @FlipOff
    public void featureWithAnnotationOverridingAtMethodLevel(){
    }

    public void featureWithSameMethodNameInDifferentClass(){
    }
}