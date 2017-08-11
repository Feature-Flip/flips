package org.flips.store;

import org.flips.TestFeatureFlipContextConfiguration;
import org.flips.fixture.TestClientFeatureFlipAnnotationsWithAnnotationsAtClassLevel;
import org.flips.fixture.TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel;
import org.flips.fixture.TestClientFeatureFlipAnnotationsWithSpringExpressionAnnotations;
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
@ContextConfiguration(classes = TestFeatureFlipContextConfiguration.class)
@TestPropertySource(properties = {"feature.disabled=false", "feature.enabled=true"})
@ActiveProfiles("dev")
public class FeatureFlipAnnotationMetaDataStoreIntegrationTest {

    @Autowired
    private FeatureFlipAnnotationMetaDataStore featureFlipAnnotationMetaDataStore;

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsSetAsEnabled() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("enabledFeature");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetAsDisabled() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("disabledFeature");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsSetWithPastCutoffDateTimeFlipStrategy() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("pastDateFeature");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetWithFutureCutoffDateTimeFlipStrategy() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("futureDateFeature");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureIsSetWithPastCutoffDateTimeFlipStrategyAndFeaturePropertyIsDisabled() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("pastDateFeatureWithDisabledSpringProperty");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsSetWithPastCutoffDateTimeFlipStrategyAndFeaturePropertyIsEnabled() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("pastDateFeatureWithEnabledSpringProperty");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenActiveProfileIsOneOfExpectedProfiles() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("springProfilesFeature");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenNoFeatureFlipConditionIsPresent() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("noFeatureConditionsAppliedEnabledByDefault");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenFeatureIsEnabledAtClassLevel() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtClassLevel.class.getMethod("featureWithAnnotationsAtClassLevel");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenFeatureOverriddenAtMethodLevel() throws Exception {
        Method method  = TestClientFeatureFlipAnnotationsWithAnnotationsAtClassLevel.class.getMethod("featureWithAnnotationOverridingAtMethodLevel");
        boolean result = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenSpringExpressionWithEnvironmentPropertyEvaluatesToFalse() throws Exception{
        Method method   = TestClientFeatureFlipAnnotationsWithSpringExpressionAnnotations.class.getMethod("featureWithSpringExpressionBasedOnEnvironmentProperty");
        boolean result  = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenSpringExpressionWithBeanReferenceEvaluatesToTrue() throws Exception{
        Method method   = TestClientFeatureFlipAnnotationsWithSpringExpressionAnnotations.class.getMethod("featureWithSpringExpressionBasedOnBeanReference");
        boolean result  = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureEnabledGivenSpringExpressionWithBeanReferenceWithMethodInvocationOnPropertyValueEvaluatesToTrue() throws Exception{
        Method method   = TestClientFeatureFlipAnnotationsWithSpringExpressionAnnotations.class.getMethod("featureWithSpringExpressionBasedOnBeanReferenceWithMethodInvocationOnPropertyValue");
        boolean result  = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureDisabledGivenSpringExpressionWithNonBeanReferenceEvaluatesToFalse() throws Exception{
        Method method   = TestClientFeatureFlipAnnotationsWithSpringExpressionAnnotations.class.getMethod("featureWithSpringExpressionBasedOnNonBeanReference");
        boolean result  = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }

    @Test
    public void shouldReturnFeatureWithSameNameInDifferentClassEnabledGivenFeatureIsSetToEnabledWithFeatureFlipAnnotation() throws Exception {
        Method method   = TestClientFeatureFlipAnnotationsWithAnnotationsAtClassLevel.class.getMethod("featureWithSameMethodNameInDifferentClass");
        boolean result  = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(true, result);
    }

    @Test
    public void shouldReturnFeatureWithSameNameInDifferentClassDisabledGivenFeatureIsSetToDisabledWithFeatureFlipAnnotation() throws Exception {
        Method method   = TestClientFeatureFlipAnnotationsWithAnnotationsAtMethodLevel.class.getMethod("featureWithSameMethodNameInDifferentClass");
        boolean result  = featureFlipAnnotationMetaDataStore.isFeatureEnabled(method);

        assertEquals(false, result);
    }
}