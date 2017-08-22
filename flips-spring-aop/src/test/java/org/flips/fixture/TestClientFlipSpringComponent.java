package org.flips.fixture;

import org.flips.annotation.Flips;
import org.flips.annotation.condition.FlipOff;
import org.springframework.stereotype.Component;

@Component
@Flips
public class TestClientFlipSpringComponent {

    @FlipOff
    public void disabledMethod(){
    }

    public boolean enabledMethod(){
        return true;
    }
}