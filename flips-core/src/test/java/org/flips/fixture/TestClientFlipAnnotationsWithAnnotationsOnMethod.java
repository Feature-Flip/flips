package org.flips.fixture;

import org.flips.annotation.*;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class TestClientFlipAnnotationsWithAnnotationsOnMethod {

    @FlipOff
    public void disabledFeature(){

    }

    @FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
    public void featureWithCurrentDtGtProvidedDt(){
    }

    @FlipOnDateTime(cutoffDateTimeProperty = "future.feature.date")
    public void featureWithCurrentDtLtProvidedDt(){
    }

    @FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
    @FlipOnEnvironmentProperty(property = "feature.disabled")
    public void featureWithCurrentDtGtProvidedDtWithDisabledSpringProperty(){
    }

    @FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
    @FlipOnEnvironmentProperty(property = "feature.enabled")
    public void featureWithCurrentDtLtProvidedDtWithEnabledSpringProperty(){
    }

    @FlipOnProfiles(activeProfiles = {"dev", "qa"})
    public void springProfilesFeature(){
    }

    public void noFeatureConditionsAppliedEnabledByDefault(){
    }

    @FlipOff
    public void featureWithSameMethodNameInDifferentClass(){
    }

    @FlipOnDateTime(cutoffDateTimeProperty = "past.feature.date")
    @FlipOnEnvironmentProperty(property = "feature.enabled")
    @FlipOff
    public void featureWithFlipOffAndConditionBasedAnnotations(){
    }

    @FlipOnDaysOfWeek(daysOfWeek = {DayOfWeek.MONDAY,
                                  DayOfWeek.TUESDAY,
                                  DayOfWeek.WEDNESDAY,
                                  DayOfWeek.THURSDAY,
                                  DayOfWeek.FRIDAY,
                                  DayOfWeek.SATURDAY,
                                  DayOfWeek.SUNDAY})
    public void featureWithDayOfWeekConditionBasedAnnotation(){
    }
}