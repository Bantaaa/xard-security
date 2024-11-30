package org.banta.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.*;

/**
 * Annotation to restrict access to users with a specific role.
 * The role value will be checked against the role hierarchy defined in SecurityProperties.
 *
 * Usage example:
 * @HasRole("ADMIN")
 * public void adminOnlyMethod() {}
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@securityExpressionHandler.hasRole(authentication, #role)")
public @interface HasRole {
    /**
     * The role required to access the resource.
     * Role will be checked against the role hierarchy, so users with higher roles will also have access.
     * @return the required role name
     */
    String value();
}