package org.flips.describe;

import org.flips.annotation.FlipScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@FlipScan(basePackages = "org.flips")
@EnableWebMvc
public class TestFlipWebContextConfiguration {
}