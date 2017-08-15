package org.flips.fixture;

import org.springframework.stereotype.Component;

@Component
public class TestClientFeatureFlipSpringComponent {

    public boolean shouldGoLive(){
        return true;
    }

    public String deploymentEnvironment(){
        return "Development".toUpperCase();
    }
}