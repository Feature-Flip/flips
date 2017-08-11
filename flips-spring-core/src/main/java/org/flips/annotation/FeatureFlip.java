package org.flips.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the intention to flip the feature. FeatureFlip annotation can be used at METHOD level and TYPE level.
 * In order to disable all the features in a given class just use -
 * <pre>
 *    &#064;FeatureFlip
 *    public class SampleClass {
 *        ...
 *    }
 * </pre>
 * Above snippet will disable all the accessible methods in SampleClass.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureFlip {

    /**
     * Returns if the feature is enabled or not. By default, the feature is disabled
     *
     * @return boolean
     */
    boolean enabled() default false;
}