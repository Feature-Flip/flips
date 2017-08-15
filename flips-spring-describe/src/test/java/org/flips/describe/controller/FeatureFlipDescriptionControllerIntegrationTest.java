package org.flips.describe.controller;

import org.flips.describe.TestFeatureFlipDescriptionContextConfiguration;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestFeatureFlipDescriptionContextConfiguration.class)
@WebAppConfiguration
public class FeatureFlipDescriptionControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnAllFeatureDescriptionGivenFeaturesWereCached() throws Exception {
       mvc.perform(MockMvcRequestBuilders.get("/describe/features"))
           .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
           .andExpect(MockMvcResultMatchers.jsonPath("$[0].feature", Matchers.equalTo("feature1")))
           .andExpect(MockMvcResultMatchers.jsonPath("$[0].class",   Matchers.equalTo("org.flips.describe.fixture.TestClientFeatureFlipAnnotationsDescription")))
           .andExpect(MockMvcResultMatchers.jsonPath("$[0].enabled", Matchers.equalTo(true)))
           .andExpect(MockMvcResultMatchers.jsonPath("$[1].feature", Matchers.equalTo("feature2")))
           .andExpect(MockMvcResultMatchers.jsonPath("$[1].class",   Matchers.equalTo("org.flips.describe.fixture.TestClientFeatureFlipAnnotationsDescription")))
           .andExpect(MockMvcResultMatchers.jsonPath("$[1].enabled", Matchers.equalTo(false)))
           .andExpect(MockMvcResultMatchers.jsonPath("$[2].feature", Matchers.equalTo("feature3")))
           .andExpect(MockMvcResultMatchers.jsonPath("$[2].class",   Matchers.equalTo("org.flips.describe.fixture.TestClientFeatureFlipAnnotationsDescription")))
           .andExpect(MockMvcResultMatchers.jsonPath("$[2].enabled", Matchers.equalTo(true)));
    }
}