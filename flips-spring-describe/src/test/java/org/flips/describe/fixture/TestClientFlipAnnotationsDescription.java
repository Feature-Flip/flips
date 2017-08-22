package org.flips.describe.fixture;


import org.flips.annotation.Flips;
import org.flips.annotation.condition.FlipOnDateTime;
import org.flips.annotation.condition.FlipOff;
import org.springframework.stereotype.Component;

@Flips
@Component
@FlipOnDateTime(cutoffDateTime = "2014-07-25")
public class TestClientFlipAnnotationsDescription {

    public void feature1(){
    }

    @FlipOff
    public void feature2(){
    }

    public void feature3(){
    }
}