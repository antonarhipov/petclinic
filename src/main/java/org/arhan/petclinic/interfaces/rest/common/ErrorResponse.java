package org.arhan.petclinic.interfaces.rest.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Response DTO for error messages.
 */
public record ErrorResponse(
    String message,
    String code,
    LocalDateTime timestamp,
    List<String> details
) {
    /**
     * Creates a new error response with a single message.
     *
     * @param message the error message
     * @param code the error code
     * @return a new error response
     */
    public static ErrorResponse of(String message, String code) {
        return new ErrorResponse(message, code, LocalDateTime.now(), new ArrayList<>());
    }

    /**
     * Creates a new error response with details.
     *
     * @param message the error message
     * @param code the error code
     * @param details the error details
     * @return a new error response
     */
    public static ErrorResponse of(String message, String code, List<String> details) {
        return new ErrorResponse(message, code, LocalDateTime.now(), details);
    }
}