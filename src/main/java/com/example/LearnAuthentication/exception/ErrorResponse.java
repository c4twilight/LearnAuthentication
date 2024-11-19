package com.example.LearnAuthentication.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
class ErrorResponse {
    private int status;
    private String message;
    private String additionalInfo;
    private String stackTrace;
}
