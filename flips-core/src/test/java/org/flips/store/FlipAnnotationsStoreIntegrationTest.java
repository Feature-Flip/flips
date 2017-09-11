package org.flips.store;

import org.flips.config.FlipContextConfiguration;
import org.flips.fixture.TestClientFlipAnnotationsWithAnnotationsOnMethod;
import org.flips.fixture.TestClientFlipAnnotationsWithSpringExpressionAnnotations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlipContextConfiguration.class)
@TestPropertySource(properties = {"feature.disabled=false", "feature.enabled=true", "past.feature.date=2014-12-30T14:00:00Z", "future.feature.date=3030-12-30T14:00:00Z"})
@ActiveProfiles("dev")
public class FlipAnnotationsStoreIntegrationTest {

    @Autowired
    private FlipAnnotationsStore flipAnnotationsStore;

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetAsDisabled() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("disabledFeature");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsSetWithPastCutoffDateTimeFlipCondition() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("featureWithCurrentDtGtProvidedDt");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetWithFutureCutoffDateTimeFlipCondition() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("featureWithCurrentDtLtProvidedDt");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetWithPastCutoffDateTimeFlipConditionAndFeaturePropertyIsDisabled() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("featureWithCurrentDtGtProvidedDtWithDisabledSpringProperty");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsSetWithPastCutoffDateTimeFlipConditionAndFeaturePropertyIsEnabled() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("featureWithCurrentDtLtProvidedDtWithEnabledSpringProperty");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenActiveProfileIsOneOfExpectedProfiles() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("springProfilesFeature");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenNoFlipConditionIsPresent() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("noFeatureConditionsAppliedEnabledByDefault");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenSpringExpressionWithEnvironmentPropertyEvaluatesToFalse() throws Exception{
        Method method   = TestClientFlipAnnotationsWithSpringExpressionAnnotations.class.getMethod("featureWithSpringExpressionBasedOnEnvironmentProperty");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenSpringExpressionWithBeanReferenceEvaluatesToTrue() throws Exception{
        Method method   = TestClientFlipAnnotationsWithSpringExpressionAnnotations.class.getMethod("featureWithSpringExpressionBasedOnBeanReference");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenSpringExpressionWithBeanReferenceWithMethodInvocationOnPropertyValueEvaluatesToTrue() throws Exception{
        Method method   = TestClientFlipAnnotationsWithSpringExpressionAnnotations.class.getMethod("featureWithSpringExpressionBasedOnBeanReferenceWithMethodInvocationOnPropertyValue");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenSpringExpressionWithNonBeanReferenceEvaluatesToFalse() throws Exception{
        Method method   = TestClientFlipAnnotationsWithSpringExpressionAnnotations.class.getMethod("featureWithSpringExpressionBasedOnNonBeanReference");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureWithSameNameInDifferentClassDisabledGivenFeatureIsSetToDisabled() throws Exception {
        Method method   = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("featureWithSameMethodNameInDifferentClass");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsDisabledByEvaluatingAllOfTheConditions() throws Exception {
        Method method   = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("featureWithFlipOffAndConditionBasedAnnotations");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureWithDayOfWeekEvaluatesToTrue() throws Exception{
        Method method   = TestClientFlipAnnotationsWithAnnotationsOnMethod.class.getMethod("featureWithDayOfWeekConditionBasedAnnotation");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }
}