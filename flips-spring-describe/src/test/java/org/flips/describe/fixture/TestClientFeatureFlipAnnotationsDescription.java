package org.flips.describe.fixture;


import org.flips.annotation.Flips;
import org.flips.annotation.strategy.CutOffDateTimeFlipStrategy;
import org.flips.annotation.strategy.DisabledFeatureFlipStrategy;
import org.springframework.stereotype.Component;

@Flips
@Component
@CutOffDateTimeFlipStrategy(cutoffDateTime = "2014-07-25")
public class TestClientFeatureFlipAnnotationsDescription {

    public void feature1(){
    }

    @DisabledFeatureFlipStrategy
    public void feature2(){
    }

    public void feature3(){
    }
}