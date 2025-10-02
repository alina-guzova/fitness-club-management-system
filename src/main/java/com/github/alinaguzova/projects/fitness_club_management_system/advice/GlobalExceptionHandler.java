package com.github.alinaguzova.projects.fitness_club_management_system.advice;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.error.ErrorResponse;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientSubscriptionException;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchSubscriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST API.
 * Intercepts and processes application-specific and framework-level exceptions,
 * returning structured {@link ErrorResponse} objects with appropriate HTTP status codes.
 * Includes custom translation of SQL constraint violations via {@link ConstraintTranslator}.
 * Applied automatically to all controllers via {@code @RestControllerAdvice}.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ConstraintTranslator constraintTranslator;

    /**
     * Handles {@link NoSuchClientException} when a client is not found.
     * Returns {@code 404 Not Found} with error details.
     *
     * @param ex the thrown exception
     * @return structured error response
     */
    @ExceptionHandler(NoSuchClientException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchClient(NoSuchClientException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                ex.getDetails()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles {@link NoSuchSubscriptionException} when a subscription is not found.
     * Returns {@code 404 Not Found} with error details.
     *
     * @param ex the thrown exception
     * @return structured error response
     */
    @ExceptionHandler(NoSuchSubscriptionException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchSubscription(NoSuchSubscriptionException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                ex.getDetails()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles {@link NoSuchClientSubscriptionException} when a client-subscription link is not found.
     * Returns {@code 404 Not Found} with error details.
     *
     * @param ex the thrown exception
     * @return structured error response
     */
    @ExceptionHandler(NoSuchClientSubscriptionException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchSubscription(NoSuchClientSubscriptionException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                ex.getDetails()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles validation errors from {@link MethodArgumentNotValidException}.
     * Aggregates field-level errors and returns {@code 400 Bad Request}.
     *
     * @param ex the thrown exception
     * @return structured error response with validation messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex){
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("Поле '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Ошибка валидации",
                errorList
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles {@link DataIntegrityViolationException} for database constraint violations.
     * Translates SQL messages using {@link ConstraintTranslator} and returns {@code 409 Conflict} or {@code 400 Bad Request}.
     *
     * @param ex the thrown exception
     * @return structured error response
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex){
        Throwable root = ex.getRootCause();

        if(root instanceof SQLIntegrityConstraintViolationException){
            List<String> userMessage = constraintTranslator.translateToList(root.getMessage());
            ErrorResponse error = new ErrorResponse(
                    HttpStatus.CONFLICT.value(),
                    "Нарушение уникальности",
                    userMessage
            );

            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Ошибка целостности данных"

        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles {@link HttpRequestMethodNotSupportedException} when an HTTP method is not allowed.
     * Returns {@code 405 Method Not Allowed} with supported methods listed.
     *
     * @param ex the thrown exception
     * @return structured error response
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex){
        String method = ex.getMethod();
        List<String> supportedMethods = Arrays.asList(ex.getSupportedMethods());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Метод " + method + " не поддерживается. Допустимые методы" + supportedMethods
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    /**
     * Handles {@link HttpMediaTypeNotSupportedException} when the request content type is unsupported.
     * Returns {@code 415 Unsupported Media Type} with expected formats.
     *
     * @param ex the thrown exception
     * @return structured error response
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex){
        String unsupported = ex.getContentType() != null ? ex.getContentType().toString() : "неизвестный формат";
        List<String> supported = ex.getSupportedMediaTypes().stream()
                .map(MediaType::toString)
                .collect(Collectors.toList());


        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                "Формат " + unsupported + " не поддерживается. Ожидается: " + supported
        );

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);

    }

    /**
     * Handles all other uncaught exceptions.
     * Returns {@code 400 Bad Request} with exception message.
     *
     * @param ex the thrown exception
     * @return generic error response
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
