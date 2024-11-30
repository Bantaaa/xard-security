package org.banta.security.service;

import org.banta.security.dto.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthResponseBuilder implements AuthResponseBuilder {
    @Override
    public AuthResponse buildResponse(UserDetails userDetails, String token) {
        return AuthResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .role(userDetails.getAuthorities().stream()
                        .findFirst()
                        .map(Object::toString)
                        .orElse(null))
                .build();
    }
}