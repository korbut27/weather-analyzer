package com.example.weatheranalyzer.web.controller;

import com.example.weatheranalyzer.domain.exception.InvalidDateRangeException;
import com.example.weatheranalyzer.web.dto.validation.ValidationErrorResponse;
import com.example.weatheranalyzer.web.dto.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ValidationErrorResponse> handleInvalidDateRangeException(InvalidDateRangeException ex) {
        List<Violation> violations = new ArrayList<>();
        violations.add(new Violation("from", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResponse(violations));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<Violation> violations = determineViolationsFromException(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResponse(violations));
    }

    private List<Violation> determineViolationsFromException(HttpMessageNotReadableException ex) {
        List<Violation> violations = new ArrayList<>();
        String exceptionMessage = ex.getCause().getMessage();

        if (exceptionMessage.contains("com.example.weatheranalyzer.web.dto.date.DateRangeRequest[\"from\"]")) {
            violations.add(new Violation("from", "Error in the format of transmitted data for 'from' field"));
        }
        if (exceptionMessage.contains("com.example.weatheranalyzer.web.dto.date.DateRangeRequest[\"to\"]")) {
            violations.add(new Violation("to", "Error in the format of transmitted data for 'to' field"));
        }
        if (exceptionMessage.contains("\"from\" is null")) {
            violations.add(new Violation("from", "Field 'from' is empty"));
        }
        if (exceptionMessage.contains("\"to\" is null")) {
            violations.add(new Violation("from", "Field 'to' is empty"));
        }
        if (violations.isEmpty()) {
            violations.add(new Violation("unknown", "Unknown error in the format of transmitted data"));
        }
        return violations;
    }
}
