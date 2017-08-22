package org.flips;

import org.flips.annotation.FlipScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@FlipScan(basePackages = "org.flips")
@EnableAspectJAutoProxy
public class TestAdvicedFlipContextConfiguration {
}