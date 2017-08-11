package org.flips.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Flips.class)})
public @interface FeatureFlipScan {
    String[] basePackages();
}