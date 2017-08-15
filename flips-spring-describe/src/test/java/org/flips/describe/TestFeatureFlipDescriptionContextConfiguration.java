package org.flips.describe;

import org.flips.annotation.FeatureFlipScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@FeatureFlipScan(basePackages = "org.flips")
@EnableWebMvc
public class TestFeatureFlipDescriptionContextConfiguration {
}