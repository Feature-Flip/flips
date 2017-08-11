package org.flips.model;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FeatureFlipStrategyAnnotationAttributesUnitTest {

    @Test
    public void shouldReturnPropertyValueAsStringGivenInputMetaDataContainsProperty(){
        Map<String, Object> attributes = new HashMap<String, Object>(){{
            put("cutoffDateTime", "2017-09-09");
        }};
        FeatureFlipStrategyAnnotationAttributes inputMetaData = new FeatureFlipStrategyAnnotationAttributes.Builder().addAll(attributes).build();

        String propertyValue = inputMetaData.getAttributeValue("cutoffDateTime", "");
        assertEquals("2017-09-09", propertyValue);
    }

    @Test
    public void shouldReturnPropertyValueAsBlankStringGivenInputMetaDataDoesNotContainProperty(){
        Map<String, Object> attributes = new HashMap<>();
        FeatureFlipStrategyAnnotationAttributes inputMetaData = new FeatureFlipStrategyAnnotationAttributes.Builder().addAll(attributes).build();

        String propertyValue = inputMetaData.getAttributeValue("cutoffDateTime", "");
        assertEquals("", propertyValue);
    }

    @Test
    public void shouldReturnPropertyValueAsStringArrayGivenInputMetaDataContainsProperty(){
        Map<String, Object> attributes = new HashMap<String, Object>(){{
            put("activeProfiles", new String[]{"dev"});
        }};
        FeatureFlipStrategyAnnotationAttributes inputMetaData = new FeatureFlipStrategyAnnotationAttributes.Builder().addAll(attributes).build();

        String[] propertyValue = inputMetaData.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY);
        assertEquals(new String[]{"dev"}, propertyValue);
    }

    @Test
    public void shouldReturnPropertyValueAsEmptyArrayGivenInputMetaDataDoesNotContainProperty(){
        Map<String, Object> attributes = new HashMap<>();
        FeatureFlipStrategyAnnotationAttributes inputMetaData = new FeatureFlipStrategyAnnotationAttributes.Builder().addAll(attributes).build();

        String[] propertyValue = inputMetaData.getAttributeValue("activeProfiles", ArrayUtils.EMPTY_STRING_ARRAY);
        assertEquals(ArrayUtils.EMPTY_STRING_ARRAY, propertyValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenPropertyNameIsNull(){
        Map<String, Object> attributes = new HashMap<>();
        FeatureFlipStrategyAnnotationAttributes inputMetaData = new FeatureFlipStrategyAnnotationAttributes.Builder().addAll(attributes).build();

        inputMetaData.getAttributeValue(null, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenAttributesToAddAreNull(){
        Map<String, Object> attributes = null;
        new FeatureFlipStrategyAnnotationAttributes.Builder().addAll(attributes).build();
    }
}