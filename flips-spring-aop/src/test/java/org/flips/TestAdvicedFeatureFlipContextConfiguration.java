package org.flips;

import org.flips.annotation.FeatureFlipScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@FeatureFlipScan(basePackages = "org.flips")
@EnableAspectJAutoProxy
public class TestAdvicedFeatureFlipContextConfiguration {
}