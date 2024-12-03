package org.banta.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private String jwtSecret;
    private Long jwtExpiration = 86400000L;
    private String[] allowedOrigins = {"*"};
    private String[] allowedMethods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
    private String[] publicPaths = {"/auth/**", "/public/**"};
}