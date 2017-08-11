package org.flips.annotation.strategy;

import org.flips.strategy.FeatureFlipStrategyCondition;

import java.lang.annotation.*;

/**
 * Indicates the FeatureFlipStrategyCondition to use. This annotation is a MetaAnnotation can be used with other annotations.
 * FeatureFlipStrategyCondition defines the condition which if met would enable feature else the feature will be considered as disabled.
 * Various implementations of FeatureFlipStrategyCondition are provided by default -
 * <ul>
 *     <li>
 *          CutOffDateTimeFlipStrategyCondition
 *     </li>
 *     <li>
 *          SpringEnvironmentPropertyFlipStrategyCondition
 *     </li>
 *     <li>
 *          SpringProfileFlipStrategyCondition
 *     </li>
 * </ul>
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureFlipStrategy {

    /**
     * returns FeatureFlipStrategyCondition which is used to evaluate a condition for a feature to be enabled.
     *
     * @return Class<? extends FeatureFlipStrategyCondition>
     */
    Class<? extends FeatureFlipStrategyCondition> value();
}