package org.flips.fixture;

import org.springframework.stereotype.Component;

@Component
public class TestClientFlipSpringComponent {

    public boolean shouldGoLive(){
        return true;
    }

    public String deploymentEnvironment(){
        return "Development".toUpperCase();
    }
}