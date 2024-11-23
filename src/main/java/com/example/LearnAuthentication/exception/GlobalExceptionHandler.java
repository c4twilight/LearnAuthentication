package com.example.LearnAuthentication.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

        String httpMethod = request instanceof ServletWebRequest ?
                ((ServletWebRequest) request).getRequest().getMethod() : "Unknown";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(errorMessage)
                .errorType("Method Not Allowed")
                .additionalInfo("The HTTP method is not allowed for the requested URL.")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .errorCode(status.name())
                .httpMethod(httpMethod)
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage = "Invalid input format.";
        if (ex.getCause() instanceof InvalidFormatException cause) {
            if (cause.getTargetType().isEnum()) {
                errorMessage = String.format(
                        "Invalid value '%s'. Allowed values are: %s",
                        cause.getValue(),
                        getEnumValues(cause.getTargetType())
                );
            }
        }

        HttpStatus status = HttpStatus.BAD_REQUEST;

        String httpMethod = request instanceof ServletWebRequest ?
                ((ServletWebRequest) request).getRequest().getMethod() : "Unknown";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(errorMessage)
                .errorType("Bad Request")
                .additionalInfo("Invalid input format")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .errorCode(status.name())
                .httpMethod(httpMethod)
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex, WebRequest request) {
        String errorMessage = "You do not have permission to access this resource.";
        HttpStatus status = HttpStatus.FORBIDDEN;

        String httpMethod = request instanceof ServletWebRequest ?
                ((ServletWebRequest) request).getRequest().getMethod() : "Unknown";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(errorMessage)
                .errorType("Access Denied")
                .additionalInfo(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .errorCode(status.name())
                .httpMethod(httpMethod)
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(org.springframework.security.core.AuthenticationException ex, WebRequest request) {
        String errorMessage = "Authentication failed. Please check your credentials.";
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        String httpMethod = request instanceof ServletWebRequest ?
                ((ServletWebRequest) request).getRequest().getMethod() : "Unknown";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(errorMessage)
                .errorType("Authentication Failure")
                .additionalInfo(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .errorCode(status.name())
                .httpMethod(httpMethod)
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }


    /*@ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        String errorMessage = "Your session has expired. Please login again.";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String httpMethod = request instanceof ServletWebRequest ?
                ((ServletWebRequest) request).getRequest().getMethod() : "Unknown";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(errorMessage)
                .errorType("Authentication Expiration")
                .additionalInfo(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .errorCode("JWT_EXPIRED")
                .httpMethod(httpMethod)
                .userFriendlyMessage("Your JWT token has expired. Please login again to continue.")
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }*/


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        ex.printStackTrace();

        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred. Please try again later.";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String httpMethod = request instanceof ServletWebRequest ?
                ((ServletWebRequest) request).getRequest().getMethod() : "Unknown";

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(errorMessage)
                .errorType("Internal Server Error")
                .additionalInfo("General server error")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .errorCode(status.name())
                .httpMethod(httpMethod)
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }

    private String getEnumValues(Class<?> enumType) {
        Object[] constants = enumType.getEnumConstants();
        if (constants != null) {
            return String.join(", ", Arrays.stream(constants).map(Object::toString).toList());
        }
        return "[]";
    }
}
