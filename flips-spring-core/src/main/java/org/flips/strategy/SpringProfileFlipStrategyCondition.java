package org.flips.strategy;

import org.apache.commons.lang3.ArrayUtils;
import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import static java.util.Arrays.asList;

@Component
public class SpringProfileFlipStrategyCondition implements FeatureFlipStrategyCondition{

    private static final Logger logger = LoggerFactory.getLogger(SpringProfileFlipStrategyCondition.class);

    @Override
    public boolean evaluateCondition(FeatureContext featureContext, FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes) {
        String[] expectedProfiles = featureFlipStrategyAnnotationAttributes.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY);
        String[] activeProfiles   = featureContext.getActiveProfilesOrEmpty();

        ValidationUtils.requireNonEmpty(expectedProfiles, "activeProfiles element can not be NULL or EMPTY when using @SpringProfileFlipStrategy");
        return isAnyActiveProfileContainedInExpectedProfile(expectedProfiles, activeProfiles);
    }

    private boolean isAnyActiveProfileContainedInExpectedProfile(String[] expectedProfiles, String[] activeProfiles) {
        logger.info("SpringProfileFlipStrategyCondition: Expected profile(s) {}, active profile(s) {}", expectedProfiles, activeProfiles);
        return CollectionUtils.containsAny(asList(activeProfiles), asList(expectedProfiles));
    }
}