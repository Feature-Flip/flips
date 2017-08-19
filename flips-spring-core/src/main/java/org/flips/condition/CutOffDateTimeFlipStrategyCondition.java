package org.flips.condition;

import org.flips.model.FeatureContext;
import org.flips.model.FeatureFlipStrategyAnnotationAttributes;
import org.flips.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Component
public class CutOffDateTimeFlipStrategyCondition implements FeatureFlipStrategyCondition{

    private static final Logger logger = LoggerFactory.getLogger(CutOffDateTimeFlipStrategyCondition.class);

    private static final ZoneId UTC  = ZoneId.of("UTC");
    private DateTimeFormatterProvider dateTimeFormatterProvider;

    public CutOffDateTimeFlipStrategyCondition(){
        dateTimeFormatterProvider = new DateTimeFormatterProvider();
    }

    @Override
    public boolean evaluateCondition(FeatureContext featureContext,
                                     FeatureFlipStrategyAnnotationAttributes featureFlipStrategyAnnotationAttributes) {

        String datetime             = featureFlipStrategyAnnotationAttributes.getAttributeValue("cutoffDateTime", "");
        ValidationUtils.requireNonEmpty(datetime, "cutoffDateTime element can not be NULL or EMPTY when using @CutOffDateTimeFlipStrategy");

        return isCurrentDateTimeAfterOrEqualCutoffDateTime(getCutoffDateTime(datetime), ZonedDateTime.now(UTC));
    }

    private boolean isCurrentDateTimeAfterOrEqualCutoffDateTime(ZonedDateTime cutoffDateTime, ZonedDateTime currentUtcTime) {
        logger.info("CutOffDateTimeFlipStrategyCondition: cutoffDateTime {}, currentUtcTime {}", cutoffDateTime, currentUtcTime);
        return currentUtcTime.isEqual(cutoffDateTime) || currentUtcTime.isAfter(cutoffDateTime);
    }

    private ZonedDateTime getCutoffDateTime(String datetime){
        logger.info("CutOffDateTimeFlipStrategyCondition: parsing {}", datetime);
        for(DateTimeFormatter df: dateTimeFormatterProvider.getSupportedFormatters()){
            try{
                return ZonedDateTime.parse(datetime, df);
            }catch ( Exception e ){
            }
        }
        throw new RuntimeException("Could not parse " + datetime + ", Supported formats " + dateTimeFormatterProvider.getSupportedFormatterPatterns());
    }

    class DateTimeFormatterProvider {

        private List<DateTimeFormatter> supportedDateTimeFormatters;
        private List<String>            supportedDateTimeFormatterPatterns;

        public DateTimeFormatterProvider(){
            List<DateTimeFormatter> formatters  = new ArrayList<DateTimeFormatter>() {{
                    add(dateTimeFormatterYYYYMMDD());
                    add(dateTimeFormatterYYYYMMDDHHMMSS());
            }};
            List<String> formatterPatterns      = new ArrayList<String>(){{
                    add("yyyy-MM-dd");
                    add("yyyy-MM-dd hh:mm:ss");
            }};
            supportedDateTimeFormatters         = unmodifiableList(formatters);
            supportedDateTimeFormatterPatterns  = unmodifiableList(formatterPatterns);
        }

        public List<DateTimeFormatter> getSupportedFormatters(){
            return supportedDateTimeFormatters;
        }

        public List<String> getSupportedFormatterPatterns(){
            return supportedDateTimeFormatterPatterns;
        }

        private DateTimeFormatter dateTimeFormatterYYYYMMDD(){
            return new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR)
                    .appendLiteral("-")
                    .appendValue(ChronoField.MONTH_OF_YEAR)
                    .appendLiteral("-")
                    .appendValue(ChronoField.DAY_OF_MONTH)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter()
                    .withZone(ZoneOffset.UTC);
        }

        private DateTimeFormatter dateTimeFormatterYYYYMMDDHHMMSS(){
            return new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR)
                    .appendLiteral("-")
                    .appendValue(ChronoField.MONTH_OF_YEAR)
                    .appendLiteral("-")
                    .appendValue(ChronoField.DAY_OF_MONTH)
                    .appendLiteral(" ")
                    .appendValue(ChronoField.HOUR_OF_DAY)
                    .appendLiteral(":")
                    .appendValue(ChronoField.MINUTE_OF_HOUR)
                    .appendLiteral(":")
                    .appendValue(ChronoField.SECOND_OF_MINUTE)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter()
                    .withZone(ZoneOffset.UTC);
        }
    }
}