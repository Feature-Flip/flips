package org.flips.fixture;

import org.flips.annotation.condition.FlipOff;
import org.flips.annotation.condition.FlipOnDateTime;
import org.flips.annotation.condition.FlipOnEnvironmentProperty;
import org.springframework.stereotype.Component;

@Component
@FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
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