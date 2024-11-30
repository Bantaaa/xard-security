package org.banta.security.config;

import lombok.RequiredArgsConstructor;
import org.banta.security.dto.RegisterRequest;
import org.banta.security.model.BaseUserDetails;
import org.banta.security.service.JwtService;
import org.banta.security.util.PasswordValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.banta.security.service.UserRegistrationService;
import org.banta.security.service.RegistrationService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class RegistrationConfig {
    private final PasswordValidator passwordValidator;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnBean(UserRegistrationService.class)
    public RegistrationService registrationService(UserRegistrationService userRegistrationService) {
        return new RegistrationService(
                userRegistrationService,
                jwtService,
                passwordEncoder,
                passwordValidator
        );
    }

    @Bean
    @ConditionalOnMissingBean(UserRegistrationService.class)
    public UserRegistrationService noOpUserRegistrationService() {
        return new UserRegistrationService() {
            @Override
            public BaseUserDetails createUser(RegisterRequest request) {
                throw new UnsupportedOperationException("Registration is not enabled");
            }

            @Override
            public boolean userExists(String username) {
                return false;
            }

            @Override
            public boolean emailExists(String email) {
                return false;
            }
        };
    }
}