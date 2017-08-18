package org.flips.fixture;

import org.flips.annotation.Flips;
import org.flips.annotation.strategy.NoConditionFlipStrategy;
import org.springframework.stereotype.Component;

@Component
@Flips
public class TestClientFeatureFlipSpringComponent {

    @NoConditionFlipStrategy
    public void disabledMethod(){
    }

    @NoConditionFlipStrategy(enabled = true)
    public boolean enabledMethod(){
        return true;
    }

    public boolean unAnnotatedMethod(){
        return false;
    }
}