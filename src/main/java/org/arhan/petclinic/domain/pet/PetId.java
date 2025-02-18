package org.arhan.petclinic.domain.pet;

import java.util.UUID;

/**
 * PetId is a value object representing the unique identifier of a pet.
 * It uses UUID internally to ensure uniqueness across the system.
 */
public record PetId(String value) {
    
    public PetId {
        if (value == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Pet ID cannot be empty");
        }
        try {
            // Validate UUID format
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Pet ID must be a valid UUID", e);
        }
    }

    /**
     * Generates a new unique PetId.
     *
     * @return a new PetId instance with a randomly generated UUID
     */
    public static PetId generate() {
        return new PetId(UUID.randomUUID().toString());
    }

    /**
     * Creates a PetId from an existing UUID string.
     *
     * @param id the UUID string to create the PetId from
     * @return a new PetId instance with the provided UUID
     * @throws IllegalArgumentException if the provided string is not a valid UUID
     */
    public static PetId fromString(String id) {
        return new PetId(id);
    }
}