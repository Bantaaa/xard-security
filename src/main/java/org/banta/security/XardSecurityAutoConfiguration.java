package org.banta.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.banta.security.config.SecurityProperties;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@ComponentScan("org.banta.security")
public class XardSecurityAutoConfiguration {
    // This class enables automatic configuration of the security module
}