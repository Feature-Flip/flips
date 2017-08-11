package org.flips.model;

import org.flips.annotation.strategy.FeatureFlipStrategy;
import org.flips.annotation.strategy.SpringEnvironmentPropertyFlipStrategy;
import org.flips.annotation.strategy.SpringProfileFlipStrategy;
import org.flips.strategy.FeatureFlipStrategyCondition;
import org.flips.strategy.SpringEnvironmentPropertyFlipStrategyCondition;
import org.flips.strategy.SpringProfileFlipStrategyCondition;
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
public class FeatureFlipStrategyAnnotationMetaDataUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionGivenNullAnnotationsWhileInstantiatingFeatureFlipStrategyAnnotationMetaData(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);

        new FeatureFlipStrategyAnnotationMetaData(applicationContext, featureContext, null);
    }

    @Test
    public void shouldEvaluateFeatureFlipConditionToTrueGivenEmptyAnnotationArray(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);
        Annotation[]       annotations        = new Annotation[0];

        FeatureFlipStrategyAnnotationMetaData annotationMetaData = new FeatureFlipStrategyAnnotationMetaData(applicationContext, featureContext, annotations);
        boolean result                                           = annotationMetaData.evaluate();

        assertEquals(true, result);
        verify(applicationContext, never()).getBean(any(Class.class));
    }

    @Test
    public void shouldEvaluateFeatureFlipConditionToTrueGivenNoFeatureFlipStrategyIsDefined(){
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        FeatureContext     featureContext     = mock(FeatureContext.class);
        Annotation[]       annotations        = new Annotation[1];

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class)).thenReturn(false);

        FeatureFlipStrategyAnnotationMetaData annotationMetaData = new FeatureFlipStrategyAnnotationMetaData(applicationContext, featureContext, annotations);
        boolean result                                           = annotationMetaData.evaluate();

        assertEquals(true, result);
        verify(applicationContext, never()).getBean(any(Class.class));

        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class);
    }

    @Test
    public void shouldEvaluateFeatureFlipConditionToTrueGivenFeatureFlipStrategyIsDefinedWithConditionEvaluatingToTrue() {
        ApplicationContext  applicationContext          = mock(ApplicationContext.class);
        FeatureContext      featureContext              = mock(FeatureContext.class);
        Annotation[]        annotations                 = new Annotation[1];
        Class               conditionClass              = SpringProfileFlipStrategyCondition.class;
        SpringProfileFlipStrategyCondition condition    = mock(SpringProfileFlipStrategyCondition.class);

        FeatureFlipStrategy annotationFeatureFlipStrategy      = mock(FeatureFlipStrategy.class);
        FeatureFlipStrategyAnnotationAttributes inputMetaData  = mock(FeatureFlipStrategyAnnotationAttributes.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class)).thenReturn(true);
        when(AnnotationUtils.getAnnotation(annotations[0], FeatureFlipStrategy.class)).thenReturn(annotationFeatureFlipStrategy);
        when(annotationFeatureFlipStrategy.value()).thenReturn(conditionClass);
        when(AnnotationUtils.getAnnotationAttributes(annotations[0])).thenReturn(inputMetaData);
        when(applicationContext.getBean(conditionClass)).thenReturn(condition);
        when(condition.evaluateCondition(featureContext, inputMetaData)).thenReturn(true);

        FeatureFlipStrategyAnnotationMetaData annotationMetaData = new FeatureFlipStrategyAnnotationMetaData(applicationContext, featureContext, annotations);
        boolean result                                           = annotationMetaData.evaluate();

        assertEquals(true, result);
        verify(applicationContext).getBean(conditionClass);
        verify(condition).evaluateCondition(featureContext, inputMetaData);
        verify(annotationFeatureFlipStrategy).value();

        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class);
        AnnotationUtils.getAnnotation(annotations[0], FeatureFlipStrategy.class);
        AnnotationUtils.getAnnotationAttributes(annotations[0]);
    }

    @Test
    public void shouldEvaluateFeatureFlipConditionToFalseGivenFeatureFlipStrategyIsDefinedWithConditionEvaluatingToFalse() {
        ApplicationContext  applicationContext          = mock(ApplicationContext.class);
        FeatureContext      featureContext              = mock(FeatureContext.class);
        Annotation[]        annotations                 = new Annotation[1];
        Class               conditionClass              = SpringProfileFlipStrategyCondition.class;

        FeatureFlipStrategy annotationFeatureFlipStrategy       = mock(FeatureFlipStrategy.class);
        SpringProfileFlipStrategyCondition condition            = mock(SpringProfileFlipStrategyCondition.class);
        FeatureFlipStrategyAnnotationAttributes inputMetaData   = mock(FeatureFlipStrategyAnnotationAttributes.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class)).thenReturn(true);
        when(AnnotationUtils.getAnnotation(annotations[0], FeatureFlipStrategy.class)).thenReturn(annotationFeatureFlipStrategy);
        when(annotationFeatureFlipStrategy.value()).thenReturn(conditionClass);
        when(AnnotationUtils.getAnnotationAttributes(annotations[0])).thenReturn(inputMetaData);
        when(applicationContext.getBean(conditionClass)).thenReturn(condition);
        when(condition.evaluateCondition(featureContext, inputMetaData)).thenReturn(false);

        FeatureFlipStrategyAnnotationMetaData annotationMetaData = new FeatureFlipStrategyAnnotationMetaData(applicationContext, featureContext, annotations);
        boolean result                                           = annotationMetaData.evaluate();

        assertEquals(false, result);
        verify(applicationContext).getBean(conditionClass);
        verify(condition).evaluateCondition(featureContext, inputMetaData);
        verify(annotationFeatureFlipStrategy).value();

        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class);
        AnnotationUtils.getAnnotation(annotations[0], FeatureFlipStrategy.class);
        AnnotationUtils.getAnnotationAttributes(annotations[0]);
    }

    @Test
    public void shouldEvaluateFeatureFlipConditionToFalseGivenMultipleFeatureFlipStrategyIsDefinedWithConditionEvaluatingToFalse() {
        ApplicationContext  applicationContext          = mock(ApplicationContext.class);
        FeatureContext      featureContext              = mock(FeatureContext.class);
        Annotation[]        annotations                 = new Annotation[]{mock(SpringProfileFlipStrategy.class), mock(SpringEnvironmentPropertyFlipStrategy.class)};
        FeatureFlipStrategy annotationFlipStrategy      = mock(FeatureFlipStrategy.class);
        FeatureFlipStrategy annotationFlipStrategyOther = mock(FeatureFlipStrategy.class);

        Class               conditionClass              = SpringProfileFlipStrategyCondition.class;
        Class               conditionClassOther         = SpringEnvironmentPropertyFlipStrategyCondition.class;

        FeatureFlipStrategyCondition conditionInstance          = mock(SpringProfileFlipStrategyCondition.class);
        FeatureFlipStrategyCondition conditionInstanceOther     = mock(SpringEnvironmentPropertyFlipStrategyCondition.class);

        FeatureFlipStrategyAnnotationAttributes metaData       = mock(FeatureFlipStrategyAnnotationAttributes.class);
        FeatureFlipStrategyAnnotationAttributes metaDataOther  = mock(FeatureFlipStrategyAnnotationAttributes.class);

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class)).thenReturn(true);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[1], FeatureFlipStrategy.class)).thenReturn(true);


        when(AnnotationUtils.getAnnotation(annotations[0], FeatureFlipStrategy.class)).thenReturn(annotationFlipStrategy);
        when(AnnotationUtils.getAnnotation(annotations[1], FeatureFlipStrategy.class)).thenReturn(annotationFlipStrategyOther);

        when(annotationFlipStrategy.value()).thenReturn(conditionClass);
        when(annotationFlipStrategyOther.value()).thenReturn(conditionClassOther);

        when(AnnotationUtils.getAnnotationAttributes(annotations[0])).thenReturn(metaData);
        when(AnnotationUtils.getAnnotationAttributes(annotations[1])).thenReturn(metaDataOther);

        when(applicationContext.getBean(conditionClass)).thenReturn(conditionInstance);
        when(applicationContext.getBean(conditionClassOther)).thenReturn(conditionInstanceOther);

        when(conditionInstance.evaluateCondition(featureContext, metaData)).thenReturn(false);
        when(conditionInstanceOther.evaluateCondition(featureContext, metaDataOther)).thenReturn(true);

        FeatureFlipStrategyAnnotationMetaData annotationMetaData = new FeatureFlipStrategyAnnotationMetaData(applicationContext, featureContext, annotations);
        boolean result                                           = annotationMetaData.evaluate();

        assertEquals(false, result);
        verify(applicationContext).getBean(conditionClass);
        verify(applicationContext, never()).getBean(conditionClassOther);
        verify(conditionInstance).evaluateCondition(featureContext, metaData);
        verify(conditionInstanceOther, never()).evaluateCondition(featureContext, metaDataOther);
        verify(annotationFlipStrategy).value();
        verify(annotationFlipStrategyOther).value();


        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FeatureFlipStrategy.class);
        AnnotationUtils.isMetaAnnotationDefined(annotations[1], FeatureFlipStrategy.class);
        AnnotationUtils.getAnnotation(annotations[0], FeatureFlipStrategy.class);
        AnnotationUtils.getAnnotation(annotations[1], FeatureFlipStrategy.class);
        AnnotationUtils.getAnnotationAttributes(annotations[0]);
        AnnotationUtils.getAnnotationAttributes(annotations[1]);
    }
}