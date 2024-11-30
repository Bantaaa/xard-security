package org.banta.security.service;

import lombok.RequiredArgsConstructor;
import org.banta.security.dto.AuthRequest;
import org.banta.security.dto.AuthResponse;
import org.banta.security.exception.SecurityException;
import org.banta.security.util.PasswordValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class BaseAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordValidator passwordValidator;

    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return buildAuthResponse(userDetails, token);
    }

    public void validatePassword(String password) {
        if (!passwordValidator.isValid(password)) {
            throw new SecurityException(passwordValidator.getPasswordRequirements());
        }
    }

    // Applications will implement this to customize the auth response
    protected abstract AuthResponse buildAuthResponse(UserDetails userDetails, String token);
}