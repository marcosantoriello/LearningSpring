package org.example.ch6_1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "org.example.ch6_1")
@EnableAspectJAutoProxy
public class ProjectConfig {
}
