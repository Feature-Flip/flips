package org.flips.fixture;

import org.flips.annotation.Flips;
import org.flips.annotation.strategy.SpringExpressionFlipStrategy;

@Flips
public class TestClientFeatureFlipAnnotationsWithSpringExpressionAnnotations {

    @SpringExpressionFlipStrategy(expression = "@environment.getProperty('should.golive') == true")
    public void featureWithSpringExpressionBasedOnEnvironmentProperty(){
    }

    @SpringExpressionFlipStrategy(expression = "@testClientFeatureFlipSpringComponent.shouldGoLive() == true")
    public void featureWithSpringExpressionBasedOnBeanReference(){
    }

    @SpringExpressionFlipStrategy(expression = "T(java.lang.Math).random() * 100.0 < T(java.lang.Math).random() * 10.0")
    public void featureWithSpringExpressionBasedOnNonBeanReference(){
    }

    @SpringExpressionFlipStrategy(expression = "@testClientFeatureFlipSpringComponent.deploymentEnvironment().contains('DEV')")
    public void featureWithSpringExpressionBasedOnBeanReferenceWithMethodInvocationOnPropertyValue(){
    }
}