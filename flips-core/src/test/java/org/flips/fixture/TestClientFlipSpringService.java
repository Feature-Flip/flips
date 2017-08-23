package org.flips.fixture;

import org.flips.annotation.Flips;
import org.flips.annotation.condition.FlipOff;
import org.flips.annotation.condition.FlipOnSpringExpression;
import org.springframework.stereotype.Component;

@Component
@Flips
public class TestClientFlipSpringService {

    public boolean enabledMethod(){
        return true;
    }

    @FlipOff
    public void disabledMethod(){
    }

    @FlipOnSpringExpression(expression = "T(java.lang.Math).random() * 100.0 < T(java.lang.Math).random() * 10.0")
    public void disabledMethodUsingSpringExpression(){
    }
}