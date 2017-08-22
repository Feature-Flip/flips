package org.flips.fixture;

import org.flips.annotation.Flips;
import org.flips.annotation.condition.FlipOnSpringExpression;

@Flips
public class TestClientFlipAnnotationsWithSpringExpressionAnnotations {

    @FlipOnSpringExpression(expression = "@environment.getProperty('should.golive') == true")
    public void featureWithSpringExpressionBasedOnEnvironmentProperty(){
    }

    @FlipOnSpringExpression(expression = "@testClientFlipSpringComponent.shouldGoLive() == true")
    public void featureWithSpringExpressionBasedOnBeanReference(){
    }

    @FlipOnSpringExpression(expression = "T(java.lang.Math).random() * 100.0 < T(java.lang.Math).random() * 10.0")
    public void featureWithSpringExpressionBasedOnNonBeanReference(){
    }

    @FlipOnSpringExpression(expression = "@testClientFlipSpringComponent.deploymentEnvironment().contains('DEV')")
    public void featureWithSpringExpressionBasedOnBeanReferenceWithMethodInvocationOnPropertyValue(){
    }
}