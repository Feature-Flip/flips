package org.flips.describe.fixture;


import org.flips.annotation.Flips;
import org.flips.annotation.strategy.CutOffDateTimeFlipStrategy;
import org.flips.annotation.strategy.NoConditionFlipStrategy;
import org.springframework.stereotype.Component;

@Flips
@Component
@CutOffDateTimeFlipStrategy(cutoffDateTime = "2014-07-25")
public class TestClientFeatureFlipAnnotationsDescription {

    public void feature1(){
    }

    @NoConditionFlipStrategy
    public void feature2(){
    }

    @NoConditionFlipStrategy(enabled = true)
    public void feature3(){
    }
}