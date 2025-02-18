package org.arhan.petclinic.domain.owner;

import java.util.UUID;

/**
 * OwnerId is a value object representing the unique identifier of a pet owner.
 * It uses UUID internally to ensure uniqueness across the system.
 */
public record OwnerId(String value) {
    
    public OwnerId {
        if (value == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Owner ID cannot be empty");
        }
        try {
            // Validate UUID format
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Owner ID must be a valid UUID", e);
        }
    }

    /**
     * Generates a new unique OwnerId.
     *
     * @return a new OwnerId instance with a randomly generated UUID
     */
    public static OwnerId generate() {
        return new OwnerId(UUID.randomUUID().toString());
    }

    /**
     * Creates an OwnerId from an existing UUID string.
     *
     * @param id the UUID string to create the OwnerId from
     * @return a new OwnerId instance with the provided UUID
     * @throws IllegalArgumentException if the provided string is not a valid UUID
     */
    public static OwnerId fromString(String id) {
        return new OwnerId(id);
    }
}