# Xard Security Starter

A lightweight Spring Boot starter for JWT authentication and authorization.

## Quick Start

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.banta</groupId>
    <artifactId>xard-security</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Minimal Setup

1. Configure `application.yaml`:
```yaml
security:
  jwt-secret: "your-very-secure-secret-key-at-least-32-chars-long"  # Must be at least 256 bits
```

2. Create an entity implementing `BaseUserDetails`:
```java
@Entity
public class User implements BaseUserDetails {
    // Implement required methods
}
```

3. Create a repository:
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

4. Create a service implementing `UserDetailsService`:
```java
@Service
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Implement user lookup
    }
}
```

That's it! You now have access to:
- `/auth/login` endpoint for authentication
- JWT-based security for all other endpoints
- Basic role-based authorization

## Available Endpoints

### Login
```http
POST /auth/login
Content-Type: application/json

{
    "username": "user",
    "password": "password"
}
```

### Register (requires additional setup)
```http
POST /auth/register
Content-Type: application/json

{
    "username": "newuser",
    "password": "password",
    "email": "user@example.com"
}
```

## Additional Features (Optional)

### Registration Support
Implement `UserRegistrationService` to enable user registration:
```java
@Service
public class CustomRegistrationService implements UserRegistrationService {
    // Implement required methods
}
```

### Custom Authorization
Use provided annotations for role-based access:
```java
@HasRole("ADMIN")
@GetMapping("/admin")
public ResponseEntity<?> adminOnly() {
    // Only ADMIN can access
}
```

### Configuration Options
Full configuration in `application.yaml`:
```yaml
security:
  jwt-secret: "your-secure-key"  # Required
  jwt-expiration: 86400000       # Optional (24 hours default)
  allowed-origins:               # Optional
    - http://localhost:4200
  allowed-methods:               # Optional
    - GET
    - POST
  public-paths:                  # Optional
    - /auth/**
    - /public/**
  role-hierarchy:                # Optional
    - ADMIN
    - USER
```

### Password Requirements
Default password must contain:
- At least 8 characters
- One digit
- One lowercase letter
- One uppercase letter
- One special character (@#$%^&+=)
- No whitespace

## Security Best Practices

1. Use a strong JWT secret key (min 256 bits)
2. Store sensitive config in environment variables
3. Use HTTPS in production
4. Keep dependencies updated
5. Implement proper password storage (BCrypt)

## Contributing
[Add contribution guidelines]

## License
[Add license information]
