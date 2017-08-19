package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * SpringEnvironmentPropertyFlipStrategyCondition evaluates property value and checks if the property value is equals to the expected value.
 * Value of the property is looked up from Spring Environment and compared against expected value, returns TRUE if both these values are equal, FALSE otherwise.
 * Comparison of Spring Property and Expected Value is case-insensitive.
 * Both, Spring Property value and Expected Value should be NON-NULL & NON-EMPTY
 */

@Component
public class SpringEnvironmentPropertyFlipStrategyCondition implements FeatureFlipStrategyCondition {

    private static final Logger logger = LoggerFactory.getLogger(SpringEnvironmentPropertyFlipStrategyCondition.class);

    @Override
    public boolean evaluateCondition(FeatureContext featureContext,
                                     FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes) {

        String property      = featureFlipStrategyAnnotationAttributes.getAttributeValue("property", "");
        String expectedValue = featureFlipStrategyAnnotationAttributes.getAttributeValue("expectedValue", "");

        ValidationUtils.requireNonEmpty(property,      "property element can not be NULL or EMPTY when using @SpringEnvironmentPropertyFlipStrategy");
        ValidationUtils.requireNonEmpty(expectedValue, "expectedValue element can not be NULL or EMPTY when using @SpringEnvironmentPropertyFlipStrategy");

        logger.info("SpringEnvironmentPropertyFlipStrategyCondition: property {}, expectedValue {}", property, expectedValue);
        String propertyValue = featureContext.getPropertyValueOrDefault(property, String.class, "");
        return propertyValue.equalsIgnoreCase(expectedValue);
    }
}