package org.flips.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import(value = FlipContextConfiguration.class)
public class AdvicedFlipContextConfiguration {
}