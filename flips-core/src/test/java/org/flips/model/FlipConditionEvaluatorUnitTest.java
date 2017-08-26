package org.flips.model;

import org.flips.annotation.FlipOnEnvironmentProperty;
import org.flips.annotation.FlipOnOff;
import org.flips.annotation.FlipOnProfiles;
import org.flips.condition.FlipCondition;
import org.flips.condition.SpringEnvironmentPropertyFlipCondition;
import org.flips.condition.SpringProfileFlipCondition;
import org.flips.utils.AnnotationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnnotationUtils.class)
public class FlipConditionEvaluatorUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNullAnnotationsWhileInstantiatingDefaultFlipConditionEvaluator(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);

        new DefaultFlipConditionEvaluator(applicationContext, featureContext, null);
    }

    @Test
    public void shouldEvaluateFlipConditionToTrueGivenEmptyAnnotationArray(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);
        Annotation[]       annotations        = new Annotation[0];

        DefaultFlipConditionEvaluator conditionEvaluator = new DefaultFlipConditionEvaluator(applicationContext, featureContext, annotations);
        boolean result                                           = conditionEvaluator.evaluate();

        assertEquals(true, result);
        verify(applicationContext, never()).getBean(any(Class.class));
    }

    @Test
    public void shouldEvaluateFlipConditionToTrueGivenNoFlipConditionIsDefined(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);
        Annotation[]       annotations        = new Annotation[1];

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class)).thenReturn(false);

        DefaultFlipConditionEvaluator conditionEvaluator = new DefaultFlipConditionEvaluator(applicationContext, featureContext, annotations);
        boolean result                                   = conditionEvaluator.evaluate();

        assertEquals(true, result);
        verify(applicationContext, never()).getBean(any(Class.class));

        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class);
    }

    @Test
    public void shouldEvaluateFlipConditionToTrueGivenFlipConditionIsDefinedWithConditionEvaluatingToTrue() {
        ApplicationContext  applicationContext          = mock(ApplicationContext.class);
        FeatureContext      featureContext              = mock(FeatureContext.class);
        Annotation[]        annotations                 = new Annotation[1];
        Class               conditionClass              = SpringProfileFlipCondition.class;
        SpringProfileFlipCondition condition            = mock(SpringProfileFlipCondition.class);

        FlipOnOff annotationFlipOnOff = mock(FlipOnOff.class);
        FlipAnnotationAttributes annotationAttributes  = mock(FlipAnnotationAttributes.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class)).thenReturn(true);
        when(AnnotationUtils.getAnnotationOfType(annotations[0], FlipOnOff.class)).thenReturn(annotationFlipOnOff);
        when(annotationFlipOnOff.value()).thenReturn(conditionClass);
        when(AnnotationUtils.getAnnotationAttributes(annotations[0])).thenReturn(annotationAttributes);
        when(applicationContext.getBean(conditionClass)).thenReturn(condition);
        when(condition.evaluateCondition(featureContext, annotationAttributes)).thenReturn(true);

        DefaultFlipConditionEvaluator conditionEvaluator = new DefaultFlipConditionEvaluator(applicationContext, featureContext, annotations);
        boolean result                                   = conditionEvaluator.evaluate();

        assertEquals(true, result);
        verify(applicationContext).getBean(conditionClass);
        verify(condition).evaluateCondition(featureContext, annotationAttributes);
        verify(annotationFlipOnOff).value();

        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class);
        AnnotationUtils.getAnnotationOfType(annotations[0], FlipOnOff.class);
        AnnotationUtils.getAnnotationAttributes(annotations[0]);
    }

    @Test
    public void shouldEvaluateFlipConditionToFalseGivenFlipConditionIsDefinedWithConditionEvaluatingToFalse() {
        ApplicationContext  applicationContext          = mock(ApplicationContext.class);
        FeatureContext      featureContext              = mock(FeatureContext.class);
        Annotation[]        annotations                 = new Annotation[1];
        Class               conditionClass              = SpringProfileFlipCondition.class;

        FlipOnOff annotationFlipOnOff = mock(FlipOnOff.class);
        SpringProfileFlipCondition condition            = mock(SpringProfileFlipCondition.class);
        FlipAnnotationAttributes annotationAttributes   = mock(FlipAnnotationAttributes.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class)).thenReturn(true);
        when(AnnotationUtils.getAnnotationOfType(annotations[0], FlipOnOff.class)).thenReturn(annotationFlipOnOff);
        when(annotationFlipOnOff.value()).thenReturn(conditionClass);
        when(AnnotationUtils.getAnnotationAttributes(annotations[0])).thenReturn(annotationAttributes);
        when(applicationContext.getBean(conditionClass)).thenReturn(condition);
        when(condition.evaluateCondition(featureContext, annotationAttributes)).thenReturn(false);

        DefaultFlipConditionEvaluator conditionEvaluator = new DefaultFlipConditionEvaluator(applicationContext, featureContext, annotations);
        boolean result                                   = conditionEvaluator.evaluate();

        assertEquals(false, result);
        verify(applicationContext).getBean(conditionClass);
        verify(condition).evaluateCondition(featureContext, annotationAttributes);
        verify(annotationFlipOnOff).value();

        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class);
        AnnotationUtils.getAnnotationOfType(annotations[0], FlipOnOff.class);
        AnnotationUtils.getAnnotationAttributes(annotations[0]);
    }

    @Test
    public void shouldEvaluateFlipConditionToFalseGivenMultipleFlipConditionsAreDefinedWithOneConditionEvaluatingToFalse() {
        ApplicationContext  applicationContext          = mock(ApplicationContext.class);
        FeatureContext      featureContext              = mock(FeatureContext.class);
        Annotation[]        annotations                 = new Annotation[]{mock(FlipOnProfiles.class), mock(FlipOnEnvironmentProperty.class)};
        FlipOnOff flipCondition                         = mock(FlipOnOff.class);
        FlipOnOff flipConditionOther                    = mock(FlipOnOff.class);

        Class               conditionClass              = SpringProfileFlipCondition.class;
        Class               conditionClassOther         = SpringEnvironmentPropertyFlipCondition.class;

        FlipCondition conditionInstance          = mock(SpringProfileFlipCondition.class);
        FlipCondition conditionInstanceOther     = mock(SpringEnvironmentPropertyFlipCondition.class);

        FlipAnnotationAttributes annotationAttributes       = mock(FlipAnnotationAttributes.class);
        FlipAnnotationAttributes annotationAttributesOther  = mock(FlipAnnotationAttributes.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class)).thenReturn(true);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[1], FlipOnOff.class)).thenReturn(true);


        when(AnnotationUtils.getAnnotationOfType(annotations[0], FlipOnOff.class)).thenReturn(flipCondition);
        when(AnnotationUtils.getAnnotationOfType(annotations[1], FlipOnOff.class)).thenReturn(flipConditionOther);

        when(flipCondition.value()).thenReturn(conditionClass);
        when(flipConditionOther.value()).thenReturn(conditionClassOther);

        when(AnnotationUtils.getAnnotationAttributes(annotations[0])).thenReturn(annotationAttributes);
        when(AnnotationUtils.getAnnotationAttributes(annotations[1])).thenReturn(annotationAttributesOther);

        when(applicationContext.getBean(conditionClass)).thenReturn(conditionInstance);
        when(applicationContext.getBean(conditionClassOther)).thenReturn(conditionInstanceOther);

        when(conditionInstance.evaluateCondition(featureContext, annotationAttributes)).thenReturn(false);
        when(conditionInstanceOther.evaluateCondition(featureContext, annotationAttributesOther)).thenReturn(true);

        DefaultFlipConditionEvaluator conditionEvaluator = new DefaultFlipConditionEvaluator(applicationContext, featureContext, annotations);
        boolean result                                   = conditionEvaluator.evaluate();

        assertEquals(false, result);
        verify(applicationContext).getBean(conditionClass);
        verify(applicationContext, never()).getBean(conditionClassOther);
        verify(conditionInstance).evaluateCondition(featureContext, annotationAttributes);
        verify(conditionInstanceOther, never()).evaluateCondition(featureContext, annotationAttributesOther);
        verify(flipCondition).value();
        verify(flipConditionOther).value();


        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class);
        AnnotationUtils.isMetaAnnotationDefined(annotations[1], FlipOnOff.class);
        AnnotationUtils.getAnnotationOfType(annotations[0], FlipOnOff.class);
        AnnotationUtils.getAnnotationOfType(annotations[1], FlipOnOff.class);
        AnnotationUtils.getAnnotationAttributes(annotations[0]);
        AnnotationUtils.getAnnotationAttributes(annotations[1]);
    }
}