package org.flips.describe.controlleradvice;

import org.flips.describe.config.FlipWebContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlipWebContextConfiguration.class)
@WebAppConfiguration
public class FlipControllerAdviceIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnNotImplementedGivenFeatureIsDisabled() throws Exception {
        mockMvc.perform(get("/test/featureDisabled/"))
                .andExpect(status().is(HttpStatus.NOT_IMPLEMENTED.value()))
                .andExpect(jsonPath("$.errorMessage" ,equalTo("Feature not enabled, identified by method public void org.flips.describe.fixture.TestClientController.featureDisabled()")))
                .andExpect(jsonPath("$.featureName"  ,equalTo("featureDisabled")))
                .andExpect(jsonPath("$.className"    ,equalTo("org.flips.describe.fixture.TestClientController")));
    }

    @Test
    public void shouldReturnStatusOkGivenFeatureIsEnabled() throws Exception {
        mockMvc.perform(get("/test/featureEnabled/" ))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldInvokeAlternateBeanGivenFlipBeanWithAnnotationIsPresent() throws Exception {
        mockMvc.perform(get("/test/className/"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().string("org.flips.describe.fixture.TestClientController"));
    }

    @Test
    public void shouldReturnNotImplementedGivenFeatureIsDisabledInAlternateBean() throws Exception {
        mockMvc.perform(get("/test/methodName/"))
                .andExpect(status().is(HttpStatus.NOT_IMPLEMENTED.value()))
                .andExpect(jsonPath("$.errorMessage" ,equalTo("Feature not enabled, identified by method public void org.flips.describe.fixture.TestClientController.methodName()")))
                .andExpect(jsonPath("$.featureName"  ,equalTo("methodName")))
                .andExpect(jsonPath("$.className"    ,equalTo("org.flips.describe.fixture.TestClientController")));
    }
}