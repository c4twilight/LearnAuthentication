# Keycloak + Spring Boot Architecture

```mermaid
flowchart LR
    User["Client (Postman/Web/Mobile)"] -->|1. Login request| KC["Keycloak (Authorization Server)"]
    KC -->|2. Access Token (JWT)| User
    User -->|3. Bearer JWT| API["Spring Boot API (Resource Server)"]
    API -->|4. Fetch JWK/issuer metadata| KC
    API -->|5. Validate token signature + claims + roles| API
    API -->|6. Allow or deny endpoint access| User
```

## Security flow summary
1. Client authenticates with Keycloak.
2. Keycloak issues JWT access token.
3. Client calls Spring API with `Authorization: Bearer <token>`.
4. Spring Security validates issuer/signature and maps roles.
5. Endpoint rules and `@PreAuthorize` decide access (`200` or `403`).
