package org.flips.fixture;

import org.flips.annotation.Flips;
import org.flips.annotation.strategy.DisabledFeatureFlipStrategy;
import org.springframework.stereotype.Component;

@Component
@Flips
public class TestClientFeatureFlipSpringComponent {

    @DisabledFeatureFlipStrategy
    public void disabledMethod(){
    }

    public boolean enabledMethod(){
        return true;
    }
}