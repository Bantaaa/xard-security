package org.banta.security.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface BaseUserDetails extends UserDetails {
    String getEmail();
    void setPassword(String password);
}