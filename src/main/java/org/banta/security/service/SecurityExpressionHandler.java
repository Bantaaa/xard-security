package org.banta.security.service;

import lombok.RequiredArgsConstructor;
import org.banta.security.config.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityExpressionHandler {
    private final SecurityProperties securityProperties;

    public boolean hasRole(Authentication authentication, String requiredRole) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        int requiredRoleIndex = securityProperties.getRoleHierarchy().indexOf(requiredRole);
        if (requiredRoleIndex == -1) {
            return false;
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority().replace("ROLE_", "");
            int userRoleIndex = securityProperties.getRoleHierarchy().indexOf(role);
            if (userRoleIndex != -1 && userRoleIndex <= requiredRoleIndex) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyRole(Authentication authentication, String[] requiredRoles) {
        for (String role : requiredRoles) {
            if (hasRole(authentication, role)) {
                return true;
            }
        }
        return false;
    }
}