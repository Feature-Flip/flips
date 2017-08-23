package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FlipAnnotationAttributes;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@Component
public class DateTimeFlipCondition implements FlipCondition {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeFlipCondition.class);

    private static final ZoneId UTC  = ZoneId.of("UTC");

    @Override
    public boolean evaluateCondition(FeatureContext featureContext,
                                     FlipAnnotationAttributes flipAnnotationAttributes) {

        String dateTimeProperty = flipAnnotationAttributes.getAttributeValue("cutoffDateTimeProperty", "");
        ValidationUtils.requireNonEmpty(dateTimeProperty, "cutoffDateTimeProperty element can not be NULL or EMPTY when using @FlipOnDateTime");

        String dateTime         = featureContext.getPropertyValueOrDefault(dateTimeProperty, String.class, "");
        ValidationUtils.requireNonEmpty(dateTime, dateTimeProperty + " containing datetime can not be NULL or EMPTY when using @FlipOnDateTime");

        return isCurrentDateTimeAfterOrEqualCutoffDateTime(getCutoffDateTime(dateTime), ZonedDateTime.now(UTC));
    }

    private boolean isCurrentDateTimeAfterOrEqualCutoffDateTime(ZonedDateTime cutoffDateTime, ZonedDateTime currentUtcTime) {
        logger.info("DateTimeFlipCondition: cutoffDateTime {}, currentUtcTime {}", cutoffDateTime, currentUtcTime);
        return currentUtcTime.isEqual(cutoffDateTime) || currentUtcTime.isAfter(cutoffDateTime);
    }

    private ZonedDateTime getCutoffDateTime(String datetime){
        logger.info("DateTimeFlipCondition: parsing {}", datetime);
        try{
            return OffsetDateTime.parse(datetime).atZoneSameInstant(UTC);
        }
        catch (DateTimeParseException e){
            logger.error("Could not parse " + datetime + ", expected format yyyy-MM-ddTHH:mm:ssZ");
            throw e;
        }
    }
}