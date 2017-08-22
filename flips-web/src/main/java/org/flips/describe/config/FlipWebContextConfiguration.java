package org.flips.describe.config;

import org.flips.config.FlipContextConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import(FlipContextConfiguration.class)
@EnableWebMvc
public class FlipWebContextConfiguration {
}