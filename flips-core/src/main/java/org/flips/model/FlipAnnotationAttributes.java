package org.flips.model;

import org.flips.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FlipAnnotationAttributes {

    private Map<String, Object> attributes = new HashMap<>();

    private FlipAnnotationAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    private Optional<Object> getAttributeValue(String attributeName) {
        ValidationUtils.requireNonEmpty(attributeName, "attributeName can not be NULL or EMPTY");
        return Optional.ofNullable(attributes.get(attributeName));
    }

    public <T> T getAttributeValue(String attributeName, T defaultValue){
        return (T) getAttributeValue(attributeName).orElse(defaultValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlipAnnotationAttributes that = (FlipAnnotationAttributes) o;
        return attributes.equals(that.attributes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlipAnnotationAttributes{");
        sb.append("attributes=").append(attributes);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder{

        private Map<String, Object> attributes = new HashMap<>();

        public Builder addAll(Map<String, Object> newAttributes){
            ValidationUtils.requireNonNull(newAttributes, "attributes to be added in FlipAnnotationAttributes can not be null");
            attributes.putAll(new HashMap<>(newAttributes));
            return this;
        }

        public FlipAnnotationAttributes build(){
            return new FlipAnnotationAttributes(attributes);
        }
    }
}