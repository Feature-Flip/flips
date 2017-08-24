package org.flips.model;

import org.flips.annotation.FlipOnOff;
import org.flips.utils.AnnotationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnnotationUtils.class)
public class FlipConditionEvaluatorFactoryUnitTest {

    @InjectMocks
    private FlipConditionEvaluatorFactory flipConditionEvaluatorFactory;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private FeatureContext featureContext;

    @Before
    public void setUp(){
        flipConditionEvaluatorFactory.buildEmptyFlipConditionEvaluator();
    }

    @Test
    public void shouldReturnEmptyFlipConditionEvaluatorGivenNoAnnotationsProvided(){
        FlipConditionEvaluatorFactory flipConditionEvaluatorFactory = new FlipConditionEvaluatorFactory(applicationContext, featureContext);

        FlipConditionEvaluator flipConditionEvaluator = flipConditionEvaluatorFactory.buildFlipConditionEvaluator(new Annotation[0]);
        assertTrue(flipConditionEvaluator instanceof EmptyFlipConditionEvaluator);
    }

    @Test
    public void shouldReturnDefaultFlipConditionEvaluatorGivenAnnotationsOfTypeFlipOnOffIsProvided(){
        Annotation[]  annotations = new Annotation[1];

        PowerMockito.mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class)).thenReturn(false);

        FlipConditionEvaluator flipConditionEvaluator = flipConditionEvaluatorFactory.buildFlipConditionEvaluator(annotations);

        assertTrue(flipConditionEvaluator instanceof DefaultFlipConditionEvaluator);

        PowerMockito.verifyStatic();
        AnnotationUtils.isMetaAnnotationDefined(annotations[0], FlipOnOff.class);
    }
}