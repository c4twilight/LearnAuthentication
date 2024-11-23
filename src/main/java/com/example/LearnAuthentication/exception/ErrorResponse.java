package com.example.LearnAuthentication.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int status;
    private String message;
    private String errorType;
    private String additionalInfo;
    private String stackTrace;
    private LocalDateTime timestamp;
    private String path;
    private String errorCode;
    private String httpMethod;
    private String userFriendlyMessage;
    private List<ValidationError> validationErrors;
}

