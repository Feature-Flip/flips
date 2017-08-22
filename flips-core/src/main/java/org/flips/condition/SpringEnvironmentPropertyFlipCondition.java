package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringEnvironmentPropertyFlipCondition implements FlipCondition {

    private static final Logger logger = LoggerFactory.getLogger(SpringEnvironmentPropertyFlipCondition.class);

    @Override
    public boolean evaluateCondition(FeatureContext featureContext,
                                     FlipAnnotationAttributes flipAnnotationAttributes) {

        String property      = flipAnnotationAttributes.getAttributeValue("property", "");
        String expectedValue = flipAnnotationAttributes.getAttributeValue("expectedValue", "");

        ValidationUtils.requireNonEmpty(property,      "property element can not be NULL or EMPTY when using @FlipOnEnvironmentProperty");
        ValidationUtils.requireNonEmpty(expectedValue, "expectedValue element can not be NULL or EMPTY when using @FlipOnEnvironmentProperty");

        logger.info("SpringEnvironmentPropertyFlipCondition: property {}, expectedValue {}", property, expectedValue);
        String propertyValue = featureContext.getPropertyValueOrDefault(property, String.class, "");
        return propertyValue.equalsIgnoreCase(expectedValue);
    }
}