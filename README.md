# LearnAuthentication

JWT-based authentication and refresh-token API using Spring Boot 3.

## Production-ready improvements added
- Input validation on auth, user, and refresh-token APIs.
- Structured JSON error responses (`ApiError`) with centralized exception handling.
- JWT secret and token-expiration externalized to environment variables.
- Refresh token expiration externalized to environment variables.
- Duplicate-username protection during user creation.
- Better defaults for production (`open-in-view=false`, H2 console disabled by default).
- Actuator health/info endpoints enabled.

## Environment variables
- `JWT_SECRET` (base64 value for HMAC key, min 256-bit)
- `JWT_ACCESS_TOKEN_EXPIRATION_MS` (default `900000`)
- `JWT_REFRESH_TOKEN_EXPIRATION_MS` (default `604800000`)
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- `H2_CONSOLE_ENABLED` (default `false`)

## Run
```bash
./mvnw spring-boot:run
```

For local/dev profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Custom actuator example
A custom actuator endpoint is available at:
- `/monitoring/springVersion`

Example response:
```json
{"version":"6.1.x"}
```
