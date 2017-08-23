package org.flips.fixture;

import org.flips.annotation.condition.FlipOnSpringExpression;
import org.springframework.stereotype.Component;

@Component
public class TestClientFlipAnnotationsWithSpringExpressionAnnotations {

    @FlipOnSpringExpression(expression = "@environment.getProperty('should.golive') == true")
    public void featureWithSpringExpressionBasedOnEnvironmentProperty(){
    }

    @FlipOnSpringExpression(expression = "@testClientFlipSpringComponent.shouldGoLive() == true")
    public void featureWithSpringExpressionBasedOnBeanReference(){
    }

    @FlipOnSpringExpression(expression = "T(java.lang.Math).sqrt(4) * 100.0 < T(java.lang.Math).sqrt(4) * 10.0")
    public void featureWithSpringExpressionBasedOnNonBeanReference(){
    }

    @FlipOnSpringExpression(expression = "@testClientFlipSpringComponent.deploymentEnvironment().contains('DEV')")
    public void featureWithSpringExpressionBasedOnBeanReferenceWithMethodInvocationOnPropertyValue(){
    }
}