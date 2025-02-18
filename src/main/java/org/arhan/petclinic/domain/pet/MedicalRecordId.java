package org.arhan.petclinic.domain.pet;

import java.util.UUID;

/**
 * MedicalRecordId is a value object representing the unique identifier of a medical record.
 * It uses UUID internally to ensure uniqueness across the system.
 */
public record MedicalRecordId(String value) {
    
    public MedicalRecordId {
        if (value == null) {
            throw new IllegalArgumentException("Medical record ID cannot be null");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Medical record ID cannot be empty");
        }
        try {
            // Validate UUID format
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Medical record ID must be a valid UUID", e);
        }
    }

    /**
     * Generates a new unique MedicalRecordId.
     *
     * @return a new MedicalRecordId instance with a randomly generated UUID
     */
    public static MedicalRecordId generate() {
        return new MedicalRecordId(UUID.randomUUID().toString());
    }

    /**
     * Creates a MedicalRecordId from an existing UUID string.
     *
     * @param id the UUID string to create the MedicalRecordId from
     * @return a new MedicalRecordId instance with the provided UUID
     * @throws IllegalArgumentException if the provided string is not a valid UUID
     */
    public static MedicalRecordId fromString(String id) {
        return new MedicalRecordId(id);
    }
}