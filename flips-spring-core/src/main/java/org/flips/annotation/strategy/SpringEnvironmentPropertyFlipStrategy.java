package org.flips.annotation.strategy;

import org.flips.strategy.SpringEnvironmentPropertyFlipStrategyCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FeatureFlipStrategy(value = SpringEnvironmentPropertyFlipStrategyCondition.class)
public @interface SpringEnvironmentPropertyFlipStrategy {

    /**
     * returns the environment property. E.g; email.compose. It will be looked in spring environment, can not be NULL or EMPTY
     * @return
     */
    String property();

    /**
     * returns the expected value of the property.
     * @return
     */
    String expectedValue() default "true";
}