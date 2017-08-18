package org.flips.describe.controller;

import org.flips.annotation.strategy.NoConditionFlipStrategy;
import org.flips.store.FeatureFlipAnnotationMetaDataStore;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FeatureFlipDescriptionControllerUnitTest {

    private MockMvc mvc;

    @InjectMocks
    private FeatureFlipDescriptionController featureFlipDescriptionController;

    @Mock
    private FeatureFlipAnnotationMetaDataStore featureFlipAnnotationMetaDataStore;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(featureFlipDescriptionController).build();
    }

    @Test
    public void shouldReturnAllFeatureDescriptionGivenFeaturesWereCached() throws Exception {
        Set<Method> methods = new HashSet<Method>(){{
            add(TestClientFeatureFlipAnnotations.class.getMethod("disabledFeature"));
            add(TestClientFeatureFlipAnnotations.class.getMethod("enabledFeature"));
        }} ;
        when(featureFlipAnnotationMetaDataStore.allMethodsCached()).thenReturn(methods);
        when(featureFlipAnnotationMetaDataStore.isFeatureEnabled(TestClientFeatureFlipAnnotations.class.getMethod("disabledFeature"))).thenReturn(false);
        when(featureFlipAnnotationMetaDataStore.isFeatureEnabled(TestClientFeatureFlipAnnotations.class.getMethod("enabledFeature"))).thenReturn(true);

        mvc.perform  (get("/describe/features"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].feature",  Matchers.equalTo("disabledFeature")))
           .andExpect(jsonPath("$[0].class",    Matchers.equalTo("org.flips.describe.controller.TestClientFeatureFlipAnnotations")))
           .andExpect(jsonPath("$[0].enabled",  Matchers.equalTo(false)))
           .andExpect(jsonPath("$[1].feature",  Matchers.equalTo("enabledFeature")))
           .andExpect(jsonPath("$[1].class",    Matchers.equalTo("org.flips.describe.controller.TestClientFeatureFlipAnnotations")))
           .andExpect(jsonPath("$[1].enabled",  Matchers.equalTo(true)));

        verify(featureFlipAnnotationMetaDataStore).allMethodsCached();
        verify(featureFlipAnnotationMetaDataStore).isFeatureEnabled(TestClientFeatureFlipAnnotations.class.getMethod("disabledFeature"));
        verify(featureFlipAnnotationMetaDataStore).isFeatureEnabled(TestClientFeatureFlipAnnotations.class.getMethod("enabledFeature"));
    }

    @Test
    public void shouldReturnFeatureDescriptionFilteredByNameGivenFeaturesWereCached() throws Exception {
        Set<Method> methods = new HashSet<Method>(){{
            add(TestClientFeatureFlipAnnotations.class.getMethod("disabledFeature"));
            add(TestClientFeatureFlipAnnotations.class.getMethod("enabledFeature"));
        }} ;
        when(featureFlipAnnotationMetaDataStore.allMethodsCached()).thenReturn(methods);
        when(featureFlipAnnotationMetaDataStore.isFeatureEnabled(TestClientFeatureFlipAnnotations.class.getMethod("disabledFeature"))).thenReturn(false);

        mvc.perform  (get("/describe/features/disabledFeature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",             Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].feature",  Matchers.equalTo("disabledFeature")))
                .andExpect(jsonPath("$[0].class",    Matchers.equalTo("org.flips.describe.controller.TestClientFeatureFlipAnnotations")))
                .andExpect(jsonPath("$[0].enabled",  Matchers.equalTo(false)));

        verify(featureFlipAnnotationMetaDataStore).allMethodsCached();
        verify(featureFlipAnnotationMetaDataStore).isFeatureEnabled(TestClientFeatureFlipAnnotations.class.getMethod("disabledFeature"));
        verify(featureFlipAnnotationMetaDataStore, never()).isFeatureEnabled(TestClientFeatureFlipAnnotations.class.getMethod("enabledFeature"));
    }

    @Test
    public void shouldReturnEmptyFeatureDescriptionFilteredByNameGivenNoFeaturesMatchTheGivenCriteria() throws Exception {
        Set<Method> methods = new HashSet<Method>(){{
            add(TestClientFeatureFlipAnnotations.class.getMethod("disabledFeature"));
            add(TestClientFeatureFlipAnnotations.class.getMethod("enabledFeature"));
        }} ;
        when(featureFlipAnnotationMetaDataStore.allMethodsCached()).thenReturn(methods);

        mvc.perform  (get("/describe/features/noFeature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",             Matchers.hasSize(0)));

        verify(featureFlipAnnotationMetaDataStore).allMethodsCached();
        verify(featureFlipAnnotationMetaDataStore, never()).isFeatureEnabled(any(Method.class));
        verify(featureFlipAnnotationMetaDataStore, never()).isFeatureEnabled(any(Method.class));
    }

    @Test
    public void shouldReturnEmptyFeatureDescriptionGivenNoFeaturesWereCached() throws Exception {
        Set<Method> methods = new HashSet<>();
        when(featureFlipAnnotationMetaDataStore.allMethodsCached()).thenReturn(methods);

        mvc.perform  (get("/describe/features"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",             Matchers.hasSize(0)));

        verify(featureFlipAnnotationMetaDataStore).allMethodsCached();
        verify(featureFlipAnnotationMetaDataStore, never()).isFeatureEnabled(any(Method.class));
        verify(featureFlipAnnotationMetaDataStore, never()).isFeatureEnabled(any(Method.class));
    }
}

class TestClientFeatureFlipAnnotations{
    @NoConditionFlipStrategy
    public void disabledFeature(){
    }

    @NoConditionFlipStrategy(enabled = true)
    public void enabledFeature(){
    }
}