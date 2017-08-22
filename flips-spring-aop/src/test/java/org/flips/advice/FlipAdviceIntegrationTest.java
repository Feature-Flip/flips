package org.flips.advice;

import org.flips.TestAdvicedFlipContextConfiguration;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.fixture.TestClientFlipSpringComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAdvicedFlipContextConfiguration.class)
public class FlipAdviceIntegrationTest {

    @Autowired
    private TestClientFlipSpringComponent testClientFlipSpringComponent;

    @Test
    public void shouldInvokeTheEnabledFeatureSuccessfully(){
        boolean result = testClientFlipSpringComponent.enabledMethod();
        assertEquals(true, result);
    }

    @Test(expected = FeatureNotEnabledException.class)
    public void shouldThrowFeatureNotEnabledExceptionGivenFeatureIsDisabled(){
        testClientFlipSpringComponent.disabledMethod();
    }
}