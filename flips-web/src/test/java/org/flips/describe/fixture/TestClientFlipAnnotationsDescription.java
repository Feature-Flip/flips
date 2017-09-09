package org.flips.describe.fixture;


import org.flips.annotation.FlipOff;
import org.flips.annotation.FlipOnDateTime;
import org.springframework.stereotype.Component;

@Component
public class TestClientFlipAnnotationsDescription {

    @FlipOnDateTime(cutoffDateTimeProperty = "default.date.enabled")
    public void feature1(){
    }

    @FlipOff
    public void feature2(){
    }

    @FlipOnDateTime(cutoffDateTimeProperty = "default.date.enabled")
    public void feature3(){
    }
}