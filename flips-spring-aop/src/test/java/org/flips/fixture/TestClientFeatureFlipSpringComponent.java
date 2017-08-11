package org.flips.fixture;

import org.flips.annotation.FeatureFlip;
import org.flips.annotation.Flips;
import org.springframework.stereotype.Component;

@Component
@Flips
public class TestClientFeatureFlipSpringComponent {

    @FeatureFlip
    public void disabledMethod(){
    }

    @FeatureFlip(enabled = true)
    public boolean enabledMethod(){
        return true;
    }

    public boolean unAnnotatedMethod(){
        return false;
    }
}