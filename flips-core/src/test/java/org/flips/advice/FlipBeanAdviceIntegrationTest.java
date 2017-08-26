package org.flips.advice;

import org.flips.config.FlipContextConfiguration;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.exception.FlipBeanFailedException;
import org.flips.fixture.TestClientFlipBeanSpringComponentSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlipContextConfiguration.class)
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
}