package org.arhan.petclinic.interfaces.rest.common;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;

/**
 * Global exception handler for REST controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        var details = new ArrayList<String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error instanceof FieldError ? 
                ((FieldError) error).getField() : 
                error.getObjectName();
            String message = error.getDefaultMessage();
            details.add(fieldName + ": " + message);
        });
        
        return ErrorResponse.of(
            "Validation failed",
            "VALIDATION_ERROR",
            details
        );
    }

    /**
     * Handles entity not found errors.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFound(EntityNotFoundException ex) {
        return ErrorResponse.of(
            ex.getMessage(),
            "NOT_FOUND"
        );
    }

    /**
     * Handles illegal argument errors.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
        return ErrorResponse.of(
            ex.getMessage(),
            "INVALID_INPUT"
        );
    }

    /**
     * Handles all other errors.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherErrors(Exception ex) {
        return ErrorResponse.of(
            "An unexpected error occurred",
            "INTERNAL_ERROR"
        );
    }
}