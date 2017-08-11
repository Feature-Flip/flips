package org.flips.advice;

import org.flips.TestAdvicedFeatureFlipContextConfiguration;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.fixture.TestClientFeatureFlipSpringComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAdvicedFeatureFlipContextConfiguration.class)
public class FeatureFlipAdviceIntegrationTest {

    @Autowired
    private TestClientFeatureFlipSpringComponent testClientFeatureFlipSpringComponent;

    @Test
    public void shouldInvokeTheEnabledFeatureSuccessfully(){
        boolean result = testClientFeatureFlipSpringComponent.enabledMethod();
        assertEquals(true, result);
    }

    @Test
    public void shouldInvokeTheUnAnnotatedFeatureSuccessfully(){
        boolean result = testClientFeatureFlipSpringComponent.unAnnotatedMethod();
        assertEquals(false, result);
    }

    @Test(expected = FeatureNotEnabledException.class)
    public void shouldThrowFeatureNotEnabledExceptionGivenFeatureIsDisabled(){
        testClientFeatureFlipSpringComponent.disabledMethod();
    }
}