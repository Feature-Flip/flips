package org.flips.model;

import org.flips.utils.Utils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FlipAnnotationAttributesUnitTest {

    @Test
    public void shouldReturnPropertyValueAsStringGivenAnnotationAttributesContainsProperty(){
        Map<String, Object> attributes = new HashMap<String, Object>(){{
            put("cutoffDateTimeProperty", "past.feature.date");
        }};
        FlipAnnotationAttributes annotationAttributes = new FlipAnnotationAttributes.Builder().addAll(attributes).build();

        String propertyValue = annotationAttributes.getAttributeValue("cutoffDateTimeProperty", "");
        assertEquals("past.feature.date", propertyValue);
    }

    @Test
    public void shouldReturnPropertyValueAsBlankStringGivenAnnotationAttributesDoesNotContainProperty(){
        Map<String, Object> attributes                = new HashMap<>();
        FlipAnnotationAttributes annotationAttributes = new FlipAnnotationAttributes.Builder().addAll(attributes).build();

        String propertyValue = annotationAttributes.getAttributeValue("cutoffDateTimeProperty", "");
        assertEquals("", propertyValue);
    }

    @Test
    public void shouldReturnPropertyValueAsStringArrayGivenAnnotationAttributesContainsProperty(){
        Map<String, Object> attributes = new HashMap<String, Object>(){{
            put("activeProfiles", new String[]{"dev"});
        }};
        FlipAnnotationAttributes annotationAttributes = new FlipAnnotationAttributes.Builder().addAll(attributes).build();

        String[] propertyValue = annotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY);
        assertEquals(new String[]{"dev"}, propertyValue);
    }

    @Test
    public void shouldReturnPropertyValueAsEmptyArrayGivenAnnotationAttributesDoesNotContainProperty(){
        Map<String, Object> attributes                = new HashMap<>();
        FlipAnnotationAttributes annotationAttributes = new FlipAnnotationAttributes.Builder().addAll(attributes).build();

        String[] propertyValue = annotationAttributes.getAttributeValue("activeProfiles", Utils.EMPTY_STRING_ARRAY);
        assertEquals(Utils.EMPTY_STRING_ARRAY, propertyValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenPropertyNameIsNull(){
        Map<String, Object> attributes                = new HashMap<>();
        FlipAnnotationAttributes annotationAttributes = new FlipAnnotationAttributes.Builder().addAll(attributes).build();

        annotationAttributes.getAttributeValue(null, Utils.EMPTY_STRING_ARRAY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenAttributesToAddAreNull(){
        Map<String, Object> attributes = null;
        new FlipAnnotationAttributes.Builder().addAll(attributes).build();
    }
}