package org.banta.security.service;

import lombok.RequiredArgsConstructor;
import org.banta.security.dto.AuthResponse;
import org.banta.security.dto.RegisterRequest;
import org.banta.security.exception.SecurityException;
import org.banta.security.model.BaseUserDetails;
import org.banta.security.util.PasswordValidator;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class RegistrationService {
    private final UserRegistrationService userRegistrationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    public AuthResponse register(RegisterRequest request) {
        // Validate password format
        if (!passwordValidator.isValid(request.getPassword())) {
            throw new SecurityException(passwordValidator.getPasswordRequirements());
        }

        // Check if username exists
        if (userRegistrationService.userExists(request.getUsername())) {
            throw new SecurityException("Username already exists");
        }

        // Check if email exists
        if (userRegistrationService.emailExists(request.getEmail())) {
            throw new SecurityException("Email already in use");
        }

        // Custom validation from implementing project
        userRegistrationService.validateRegistration(request);

        // Encode password
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        // Create user
        BaseUserDetails user = userRegistrationService.createUser(request);

        // Generate JWT token
        String token = jwtService.generateToken(user);

        // Build response
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getAuthorities().stream()
                        .findFirst()
                        .map(Object::toString)
                        .orElse(null))
                .build();
    }
}