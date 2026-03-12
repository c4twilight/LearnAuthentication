# LearnAuthentication

This repository now contains two learning tracks:

1. Local JWT auth flow (custom token generation/refresh in this app).
2. Keycloak + Spring Boot 3 OAuth2 Resource Server flow (external IdP).

## Tech stack
- Spring Boot 3
- Spring Security 6
- JWT
- OAuth2 Resource Server
- Keycloak (via Docker)
- H2, JPA, Redis (already present in project)

## When to use what

### Spring Security
Use this in all serious Spring apps that need authentication/authorization.  
It is the security framework layer where you define filters, sessions/stateless mode, method access rules, CORS, CSRF, etc.

### JWT
Use JWT when your APIs are stateless and you want self-contained access tokens.  
Good for microservices and mobile/web APIs where each request carries a bearer token.

### OAuth2
Use OAuth2 when your app should delegate authorization/login to an external authorization server (Keycloak/Okta/Auth0/Azure AD).  
It standardizes token issuance and validation instead of building all auth logic yourself.

### OpenID Connect (OIDC)
Use OIDC when you also need user identity (who logged in), not only API authorization.  
OIDC sits on top of OAuth2 and adds ID token + identity claims.

### Why this Keycloak setup is useful
- Centralized auth for multiple applications.
- SSO support.
- Easier role/permission management.
- Better fit for enterprise/team environments than hand-rolled auth.

## Project modes

### Mode A: Existing local JWT flow
Default profile (no `keycloak` profile).  
Uses the existing classes in this repo such as:
- `JwtAuthFilter`
- `JwtService`
- refresh token APIs

Run:
```bash
./mvnw spring-boot:run
```

### Mode B: Keycloak OAuth2 resource-server flow
Enable `keycloak` profile.

Run app:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=keycloak
```

Key profile config:
- `src/main/resources/application-keycloak.properties`
- `KEYCLOAK_ISSUER_URI` (optional env override, defaults to local realm issuer)

## Keycloak setup (Docker)

Files added:
- `docker-compose.yml`
- `docker/keycloak/realms/spring-keycloak-realm.json`

Start Keycloak + MySQL:
```bash
docker compose up -d
```

Keycloak admin console:
- `http://localhost:8181/admin`
- username: `admin`
- password: `admin`

Preloaded realm/client/users:
- Realm: `spring-keycloak-realm`
- Client ID: `spring-keycloak-client-id`
- Users:
  - `adminuser / password` (role: `ADMIN`)
  - `appuser / password` (role: `USER`)

## Keycloak demo endpoints

Controller:
- `src/main/java/com/example/LearnAuthentication/controller/KeycloakDemoController.java`

Endpoints:
- `GET /api/v1/public/hello` (no token)
- `GET /api/v1/user/hello` (`ROLE_USER`)
- `GET /api/v1/admin/hello` (`ROLE_ADMIN`)

## Get access token and test

Token endpoint:
`http://localhost:8181/realms/spring-keycloak-realm/protocol/openid-connect/token`

Example (password grant for local dev/testing):
```bash
curl --location --request POST "http://localhost:8181/realms/spring-keycloak-realm/protocol/openid-connect/token" \
  --header "Content-Type: application/x-www-form-urlencoded" \
  --data-urlencode "client_id=spring-keycloak-client-id" \
  --data-urlencode "grant_type=password" \
  --data-urlencode "username=adminuser" \
  --data-urlencode "password=password"
```

Use `access_token`:
```bash
curl --location "http://localhost:9090/api/v1/admin/hello" \
  --header "Authorization: Bearer <ACCESS_TOKEN>"
```

## Postman collection

Import:
- `postman/LearnAuthentication-Keycloak.postman_collection.json`

Suggested run order:
1. `1) Get Admin Token`
2. `2) Get User Token`
3. `3) Public Hello`
4. `4) User Hello (USER token)`
5. `5) Admin Hello (ADMIN token)`
6. `6) Admin Hello (USER token - expected 403)`

The token requests auto-save `adminAccessToken` and `userAccessToken` variables.

## Architecture diagram

See:
- `docs/keycloak-architecture.md`

## New Keycloak classes
- `src/main/java/com/example/LearnAuthentication/config/KeycloakSecurityConfig.java`
- `src/main/java/com/example/LearnAuthentication/config/KeycloakRoleConverter.java`

Also updated:
- `src/main/java/com/example/LearnAuthentication/config/SecurityConfig.java`  
  Added `@Profile("!keycloak")` to keep old JWT mode isolated from Keycloak mode.

## Notes
- This project intentionally keeps both approaches so you can compare design choices.
- For production, avoid password grant and prefer Authorization Code flow with PKCE.
