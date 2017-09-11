package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.flips.utils.DateTimeUtils;
import org.flips.utils.Utils;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Arrays;

@Component
public class DayOfWeekFlipCondition implements FlipCondition {

    private static final Logger logger = LoggerFactory.getLogger(DayOfWeekFlipCondition.class);

    @Override
    public boolean evaluateCondition(FeatureContext featureContext, FlipAnnotationAttributes flipAnnotationAttributes) {
        DayOfWeek[] enabledOnWeekDays = (DayOfWeek[])flipAnnotationAttributes.getAttributeValue("weekDays", Utils.emptyArray(DayOfWeek.class));
        DayOfWeek currentDay          = DateTimeUtils.dayOfWeek();

        ValidationUtils.requireNonEmpty(enabledOnWeekDays, "weekDays element can not be NULL or EMPTY when using @FlipOnDaysOfWeek");

        logger.info("DayOfWeekFlipCondition: Enabled on days {}, current day {}", enabledOnWeekDays, currentDay);
        return Arrays.asList(enabledOnWeekDays).contains(currentDay);
    }
}