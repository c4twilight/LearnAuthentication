package com.example.LearnAuthentication.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ValidationError {
        private String fieldName;
        private String errorMessage;
}
