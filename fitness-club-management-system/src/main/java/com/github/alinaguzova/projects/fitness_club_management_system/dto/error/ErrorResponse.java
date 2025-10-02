package com.github.alinaguzova.projects.fitness_club_management_system.dto.error;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard DTO for returning structured error information in JSON format.
 * Used in exception handling to provide consistent error responses across the API.
 * Includes HTTP status code, error message, timestamp, and optional list of detailed messages.
 * Typically returned by global exception handler via {@code @RestControllerAdvice}.
 */

public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;
    private List<String> details;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, List<String> details) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}