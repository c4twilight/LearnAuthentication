package com.example.LearnAuthentication.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>("Request method not supported", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid input.";
        if (ex.getCause() instanceof InvalidFormatException cause) {
            // Handle invalid enum value
            if (cause.getTargetType().isEnum()) {
                errorMessage = String.format(
                        "Invalid value '%s'. Allowed values are: %s",
                        cause.getValue(),
                        getEnumValues(cause.getTargetType())
                );
            }
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private String getEnumValues(Class<?> enumType) {
        Object[] constants = enumType.getEnumConstants();
        if (constants != null) {
            return String.join(", ",
                    Arrays.stream(constants).map(Object::toString).toList());
        }
        return "[]";
    }

    // Other exception handlers...
    // Catch all general exceptions and return a 500 error response with message and stack trace
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        // Log the exception if necessary
        ex.printStackTrace();

        // Create a detailed error message
        String errorMessage = "An unexpected error occurred. Please try again later.";
        String additionalInfo = "Error details: " + ex.getMessage();
        // Optionally, include the stack trace in the response for debugging
        String stackTrace = ex.getStackTrace() != null && ex.getStackTrace().length > 0 ?
                ex.getStackTrace()[0].toString() : "No stack trace available.";
        // Structure the response with all the relevant details
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage, additionalInfo, stackTrace);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}