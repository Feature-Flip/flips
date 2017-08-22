package org.flips.store;

import org.flips.TestFlipContextConfiguration;
import org.flips.fixture.TestClientFlipAnnotationsWithAnnotationsAtClassLevel;
import org.flips.fixture.TestClientFlipAnnotationsWithAnnotationsAtMethodLevel;
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
@ContextConfiguration(classes = TestFlipContextConfiguration.class)
@TestPropertySource(properties = {"feature.disabled=false", "feature.enabled=true"})
@ActiveProfiles("dev")
public class FlipAnnotationsStoreIntegrationTest {

    @Autowired
    private FlipAnnotationsStore flipAnnotationsStore;

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetAsDisabled() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("disabledFeature");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsSetWithPastCutoffDateTimeFlipCondition() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("pastDateFeature");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetWithFutureCutoffDateTimeFlipCondition() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("futureDateFeature");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetWithPastCutoffDateTimeFlipConditionAndFeaturePropertyIsDisabled() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("pastDateFeatureWithDisabledSpringProperty");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsSetWithPastCutoffDateTimeFlipConditionAndFeaturePropertyIsEnabled() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("pastDateFeatureWithEnabledSpringProperty");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenActiveProfileIsOneOfExpectedProfiles() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("springProfilesFeature");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenNoFlipConditionIsPresent() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("noFeatureConditionsAppliedEnabledByDefault");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsEnabledAtClassLevel() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtClassLevel.class.getMethod("featureWithAnnotationsAtClassLevel");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureOverriddenAtMethodLevel() throws Exception {
        Method method  = TestClientFlipAnnotationsWithAnnotationsAtClassLevel.class.getMethod("featureWithAnnotationOverridingAtMethodLevel");
        boolean result = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
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
    public void shouldReturnFeatureWithSameNameInDifferentClassEnabledGivenFeatureIsSetToEnabled() throws Exception {
        Method method   = TestClientFlipAnnotationsWithAnnotationsAtClassLevel.class.getMethod("featureWithSameMethodNameInDifferentClass");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureWithSameNameInDifferentClassDisabledGivenFeatureIsSetToDisabledWith() throws Exception {
        Method method   = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("featureWithSameMethodNameInDifferentClass");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsDisabled() throws Exception {
        Method method   = TestClientFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("featureWithFlipOffAndConditionBasedAnnotations");
        boolean result  = flipAnnotationsStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }
}