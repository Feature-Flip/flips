package org.flips.advice;

import org.flips.config.FlipContextConfiguration;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.fixture.TestClientFlipSpringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlipContextConfiguration.class)
public class FlipFeatureAdviceIntegrationTest {

    @Autowired
    private TestClientFlipSpringService testClientFlipSpringService;

    @Test
    public void shouldInvokeTheEnabledFeatureSuccessfully(){
        boolean result = testClientFlipSpringService.enabledMethod();
        assertEquals(true, result);
    }

    @Test(expected = FeatureNotEnabledException.class)
    public void shouldThrowFeatureNotEnabledExceptionGivenFeatureIsDisabled(){
        testClientFlipSpringService.disabledMethod();
    }

    @Test(expected = FeatureNotEnabledException.class)
    public void shouldThrowFeatureNotEnabledExceptionGivenFeatureIsDisabledUsingSpringExpression(){
        testClientFlipSpringService.disabledMethodUsingSpringExpression();
    }
}