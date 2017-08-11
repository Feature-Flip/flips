package org.flips;

import org.flips.annotation.FeatureFlipScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@FeatureFlipScan(basePackages = "org.flips")
public class TestFeatureFlipContextConfiguration {
}