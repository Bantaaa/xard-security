package org.banta.security.service;

import org.banta.security.dto.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

@FunctionalInterface
public interface AuthResponseBuilder {
    AuthResponse buildResponse(UserDetails userDetails, String token);
}