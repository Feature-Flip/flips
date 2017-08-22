package org.flips;

import org.flips.annotation.FlipScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@FlipScan(basePackages = "org.flips")
public class TestFlipContextConfiguration {
}