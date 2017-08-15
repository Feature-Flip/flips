package org.flips.describe.config;

import org.flips.config.FeatureFlipContextConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import(FeatureFlipContextConfiguration.class)
@EnableWebMvc
public class FeatureFlipDescriptionContextConfiguration {
}