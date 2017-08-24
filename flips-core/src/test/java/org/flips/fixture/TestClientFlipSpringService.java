package org.flips.fixture;

import org.flips.annotation.FlipOff;
import org.flips.annotation.FlipOnSpringExpression;
import org.springframework.stereotype.Component;

@Component
public class TestClientFlipSpringService {

    public boolean enabledMethod(){
        return true;
    }

    @FlipOff
    public void disabledMethod(){
    }

    @FlipOnSpringExpression(expression = "T(java.lang.Math).sqrt(4) * 100.0 < T(java.lang.Math).sqrt(4) * 10.0")
    public void disabledMethodUsingSpringExpression(){
    }
}