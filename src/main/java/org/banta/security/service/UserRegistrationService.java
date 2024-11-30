package org.banta.security.service;

import org.banta.security.dto.RegisterRequest;
import org.banta.security.model.BaseUserDetails;

public interface UserRegistrationService {
    /**
     * Create a new user from registration request
     * @param request Registration data
     * @return Created user details
     */
    BaseUserDetails createUser(RegisterRequest request);

    /**
     * Check if a user with given username exists
     * @param username Username to check
     * @return true if exists, false otherwise
     */
    boolean userExists(String username);

    /**
     * Check if a user with given email exists
     * @param email Email to check
     * @return true if exists, false otherwise
     */
    boolean emailExists(String email);

    /**
     * Validate registration request
     * @param request Registration data to validate
     * @throws SecurityException if validation fails
     */
    default void validateRegistration(RegisterRequest request) {
        // Implementing projects can override this for custom validation
    }
}