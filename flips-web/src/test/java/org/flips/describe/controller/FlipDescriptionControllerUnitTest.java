package org.flips.describe.controller;

import org.flips.annotation.FlipOff;
import org.flips.store.FlipAnnotationsStore;
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
public class FlipDescriptionControllerUnitTest {

    private MockMvc mvc;

    @InjectMocks
    private FlipDescriptionController flipDescriptionController;

    @Mock
    private FlipAnnotationsStore flipAnnotationsStore;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(flipDescriptionController).build();
    }

    @Test
    public void shouldReturnAllFeatureDescriptionGivenFeaturesWereCached() throws Exception {
        Set<Method> methods = new HashSet<Method>(){{
            add(TestClientFlipAnnotations.class.getMethod("disabledFeature"));
            add(TestClientFlipAnnotations.class.getMethod("enabledFeature"));
        }} ;
        when(flipAnnotationsStore.allMethodsCached()).thenReturn(methods);
        when(flipAnnotationsStore.isFeatureEnabled(TestClientFlipAnnotations.class.getMethod("disabledFeature"))).thenReturn(false);
        when(flipAnnotationsStore.isFeatureEnabled(TestClientFlipAnnotations.class.getMethod("enabledFeature"))).thenReturn(true);

        mvc.perform  (get("/describe/features"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].feature",  Matchers.equalTo("disabledFeature")))
           .andExpect(jsonPath("$[0].class",    Matchers.equalTo("org.flips.describe.controller.TestClientFlipAnnotations")))
           .andExpect(jsonPath("$[0].enabled",  Matchers.equalTo(false)))
           .andExpect(jsonPath("$[1].feature",  Matchers.equalTo("enabledFeature")))
           .andExpect(jsonPath("$[1].class",    Matchers.equalTo("org.flips.describe.controller.TestClientFlipAnnotations")))
           .andExpect(jsonPath("$[1].enabled",  Matchers.equalTo(true)));

        verify(flipAnnotationsStore).allMethodsCached();
        verify(flipAnnotationsStore).isFeatureEnabled(TestClientFlipAnnotations.class.getMethod("disabledFeature"));
        verify(flipAnnotationsStore).isFeatureEnabled(TestClientFlipAnnotations.class.getMethod("enabledFeature"));
    }

    @Test
    public void shouldReturnFeatureDescriptionFilteredByNameGivenFeaturesWereCached() throws Exception {
        Set<Method> methods = new HashSet<Method>(){{
            add(TestClientFlipAnnotations.class.getMethod("disabledFeature"));
            add(TestClientFlipAnnotations.class.getMethod("enabledFeature"));
        }} ;
        when(flipAnnotationsStore.allMethodsCached()).thenReturn(methods);
        when(flipAnnotationsStore.isFeatureEnabled(TestClientFlipAnnotations.class.getMethod("disabledFeature"))).thenReturn(false);

        mvc.perform  (get("/describe/features/disabledFeature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",             Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].feature",  Matchers.equalTo("disabledFeature")))
                .andExpect(jsonPath("$[0].class",    Matchers.equalTo("org.flips.describe.controller.TestClientFlipAnnotations")))
                .andExpect(jsonPath("$[0].enabled",  Matchers.equalTo(false)));

        verify(flipAnnotationsStore).allMethodsCached();
        verify(flipAnnotationsStore).isFeatureEnabled(TestClientFlipAnnotations.class.getMethod("disabledFeature"));
        verify(flipAnnotationsStore, never()).isFeatureEnabled(TestClientFlipAnnotations.class.getMethod("enabledFeature"));
    }

    @Test
    public void shouldReturnEmptyFeatureDescriptionFilteredByNameGivenNoFeaturesMatchTheGivenCriteria() throws Exception {
        Set<Method> methods = new HashSet<Method>(){{
            add(TestClientFlipAnnotations.class.getMethod("disabledFeature"));
            add(TestClientFlipAnnotations.class.getMethod("enabledFeature"));
        }} ;
        when(flipAnnotationsStore.allMethodsCached()).thenReturn(methods);

        mvc.perform  (get("/describe/features/noFeature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",             Matchers.hasSize(0)));

        verify(flipAnnotationsStore).allMethodsCached();
        verify(flipAnnotationsStore, never()).isFeatureEnabled(any(Method.class));
        verify(flipAnnotationsStore, never()).isFeatureEnabled(any(Method.class));
    }

    @Test
    public void shouldReturnEmptyFeatureDescriptionGivenNoFeaturesWereCached() throws Exception {
        Set<Method> methods = new HashSet<>();
        when(flipAnnotationsStore.allMethodsCached()).thenReturn(methods);

        mvc.perform  (get("/describe/features"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",             Matchers.hasSize(0)));

        verify(flipAnnotationsStore).allMethodsCached();
        verify(flipAnnotationsStore, never()).isFeatureEnabled(any(Method.class));
        verify(flipAnnotationsStore, never()).isFeatureEnabled(any(Method.class));
    }
}

class TestClientFlipAnnotations {
    @FlipOff
    public void disabledFeature(){
    }

    public void enabledFeature(){
    }
}