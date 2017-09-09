package org.flips.advice;

import org.flips.config.FlipContextConfiguration;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.exception.FlipBeanFailedException;
import org.flips.fixture.TestClientFlipBeanSpringComponentSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlipContextConfiguration.class)
@ActiveProfiles("dev")
@TestPropertySource(properties = {"flip.bean=1"})
public class FlipBeanAdviceIntegrationTest {

    @Autowired
    private TestClientFlipBeanSpringComponentSource testClientFlipBeanSpringComponentSource;

    @Test
    public void shouldInvokeMethodTargetClassGivenFlipBeanAnnotationIsProvided(){
        String output = testClientFlipBeanSpringComponentSource.map("flip bean operation");
        assertEquals("flip bean operation:TARGET", output);
    }

    @Test(expected = FeatureNotEnabledException.class)
    public void shouldThrowFlipBeanFailedExceptionGivenMethodOnTargetIsFlippedOff(){
        testClientFlipBeanSpringComponentSource.currentDate();
    }

    @Test(expected = FlipBeanFailedException.class)
    public void shouldThrowFlipBeanFailedExceptionGivenMethodWithTheSameNameAndParameterTypesIsNotPresentInTarget(){
        testClientFlipBeanSpringComponentSource.nextDate();
    }

    @Test(expected = FlipBeanFailedException.class)
    public void shouldThrowFlipBeanFailedExceptionGivenMethodWithTheSameNameAndParameterTypesIsNotAccessibleInTarget(){
        testClientFlipBeanSpringComponentSource.previousDate();
    }

    @Test
    public void shouldInvokeMethodTargetClassGivenFlipBeanAnnotationWithConditionEvaluatingToTrueIsProvided(){
        String output = testClientFlipBeanSpringComponentSource.changeCase("InPuT");
        assertEquals("input", output);
    }

    @Test
    public void shouldInvokeMethodTargetClassGivenFlipBeanAnnotationWithMultipleConditionsEvaluatingToTrueIsProvided(){
        String output = testClientFlipBeanSpringComponentSource.html();
        assertEquals("<HTML></HTML>", output);
    }

    @Test
    public void shouldNotInvokeMethodTargetClassGivenFlipBeanAnnotationWithConditionEvaluatingToFalseIsProvided(){
        String output = testClientFlipBeanSpringComponentSource.identity("identity");
        assertEquals("identity", output);
    }
}