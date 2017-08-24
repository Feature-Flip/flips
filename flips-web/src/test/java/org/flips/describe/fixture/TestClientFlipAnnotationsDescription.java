package org.flips.describe.fixture;


import org.flips.annotation.FlipOnDateTime;
import org.flips.annotation.FlipOff;
import org.springframework.stereotype.Component;

@Component
@FlipOnDateTime(cutoffDateTimeProperty = "default.date.enabled")
public class TestClientFlipAnnotationsDescription {

    public void feature1(){
    }

    @FlipOff
    public void feature2(){
    }

    public void feature3(){
    }
}