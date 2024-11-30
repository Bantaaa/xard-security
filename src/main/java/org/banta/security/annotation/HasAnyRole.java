package org.banta.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@securityExpressionHandler.hasAnyRole(authentication, #requiredRoles)")
public @interface HasAnyRole {
    String[] value();
}