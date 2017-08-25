package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.flips.utils.Utils;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import static java.util.Arrays.asList;

@Component
public class SpringProfileFlipCondition implements FlipCondition {

    private static final Logger logger = LoggerFactory.getLogger(SpringProfileFlipCondition.class);

    @Override
    public boolean evaluateCondition(FeatureContext featureContext, FlipAnnotationAttributes flipAnnotationAttributes) {
        String[] expectedProfiles = flipAnnotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY);
        String[] activeProfiles   = featureContext.getActiveProfilesOrEmpty();

        ValidationUtils.requireNonEmpty(expectedProfiles, "activeProfiles element can not be NULL or EMPTY when using @FlipOnProfiles");
        return isAnyActiveProfileContainedInExpectedProfile(expectedProfiles, activeProfiles);
    }

    private boolean isAnyActiveProfileContainedInExpectedProfile(String[] expectedProfiles, String[] activeProfiles) {
        logger.info("SpringProfileFlipCondition: Expected profile(s) {}, active profile(s) {}", expectedProfiles, activeProfiles);
        return CollectionUtils.containsAny(asList(activeProfiles), asList(expectedProfiles));
    }
}