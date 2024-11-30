# Xard Security Starter

A Spring Boot starter providing JWT authentication and dynamic role-based authorization.

## Features

- JWT token authentication
- Configurable role-based authorization with hierarchy
- Password validation
- Custom security annotations
- CORS configuration
- Exception handling

## Installation

Add this dependency to your pom.xml:

```xml
<dependency>
    <groupId>org.banta</groupId>
    <artifactId>xard-security</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Configuration

Add these properties to your application.yml:

```yaml
security:
  jwt-secret: your-256-bit-secret
  jwt-expiration: 86400000 # 24 hours
  allowed-origins:
    - "*"
  allowed-methods:
    - GET
    - POST
    - PUT
    - DELETE
    - OPTIONS
  public-paths:
    - /auth/**
    - /public/**
  role-hierarchy:
    - SUPER_ADMIN  # Highest priority
    - ADMIN
    - MANAGER
    - USER        # Lowest priority
```

## Required Implementations

1. Implement UserDetailsService:

```java
@Service
public class YourUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Your implementation to load user from your database
        // Must return UserDetails object
    }
}
```

2. Extend BaseAuthenticationService:

```java
@Service
public class YourAuthService extends BaseAuthenticationService {
    @Override
    protected AuthResponse buildAuthResponse(UserDetails userDetails, String token) {
        return AuthResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();
    }
}
```

## Using Security Annotations

The starter provides two main security annotations:

1. @HasAnyRole: Requires any of the specified roles
```java
@HasAnyRole({"ADMIN", "MANAGER"})
@GetMapping("/protected")
public ResponseEntity<?> protectedEndpoint() {
    // Only accessible by users with ADMIN or MANAGER role
}
```

2. @HasRoleOrIsUser: Requires either the specified role or to be the user themselves
```java
@HasRoleOrIsUser("MANAGER")
@GetMapping("/users/{id}")
public ResponseEntity<?> userEndpoint(@PathVariable Long id) {
    // Accessible by MANAGER or the user with the specified id
}
```

## Authentication Endpoints

The starter automatically provides these endpoints:

```
POST /auth/login
```
Request body:
```json
{
    "username": "user",
    "password": "password"
}
```

Response:
```json
{
    "token": "jwt-token",
    "username": "user",
    "role": "ROLE_USER"
}
```

## Password Validation

The starter includes built-in password validation with these requirements:
- Minimum 8 characters
- At least one digit
- At least one lowercase letter
- At least one uppercase letter
- At least one special character (@#$%^&+=)
- No whitespace

## Error Handling

The starter automatically handles these exceptions:
- JWT token errors
- Authentication errors
- Authorization errors
- Validation errors

## Security Headers

Automatically configured security headers:
- CORS (Configurable)
- XSS Protection
- Content Security Policy
- Frame Options
- HSTS

## Role Hierarchy

Roles defined in the `role-hierarchy` list follow a hierarchical order where roles higher in the list inherit permissions of roles below them. For example, with the default configuration:
- SUPER_ADMIN can access everything
- ADMIN can access everything except SUPER_ADMIN endpoints
- MANAGER can access everything except SUPER_ADMIN and ADMIN endpoints
- USER has the most restricted access
