package org.arhan.petclinic.domain.clinic;

import java.util.UUID;

/**
 * VeterinarianId is a value object representing the unique identifier of a veterinarian.
 * It uses UUID internally to ensure uniqueness across the system.
 */
public record VeterinarianId(String value) {
    
    public VeterinarianId {
        if (value == null) {
            throw new IllegalArgumentException("Veterinarian ID cannot be null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Veterinarian ID cannot be empty");
        }
        try {
            // Validate UUID format
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Veterinarian ID must be a valid UUID", e);
        }
    }

    /**
     * Generates a new unique VeterinarianId.
     *
     * @return a new VeterinarianId instance with a randomly generated UUID
     */
    public static VeterinarianId generate() {
        return new VeterinarianId(UUID.randomUUID().toString());
    }

    /**
     * Creates a VeterinarianId from an existing UUID string.
     *
     * @param id the UUID string to create the VeterinarianId from
     * @return a new VeterinarianId instance with the provided UUID
     * @throws IllegalArgumentException if the provided string is not a valid UUID
     */
    public static VeterinarianId fromString(String id) {
        return new VeterinarianId(id);
    }
}