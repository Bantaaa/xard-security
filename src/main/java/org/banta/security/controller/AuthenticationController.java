package org.banta.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.banta.security.dto.AuthRequest;
import org.banta.security.dto.AuthResponse;
import org.banta.security.dto.RegisterRequest;
import org.banta.security.service.XardAuthenticationService;
import org.banta.security.service.RegistrationService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final XardAuthenticationService authenticationService;
    private final ObjectProvider<RegistrationService> registrationServiceProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegistrationService registrationService = registrationServiceProvider.getIfAvailable(() -> {
            throw new SecurityException("Registration is not enabled");
        });
        return ResponseEntity.ok(registrationService.register(request));
    }
}