package org.arhan.petclinic.application.pet;

import java.time.LocalDate;

/**
 * Command object for updating an existing pet.
 */
public record UpdatePetCommand(
    String id,
    String name,
    String species,
    LocalDate birthDate,
    String ownerId
) {
    /**
     * Validates the command data.
     *
     * @throws IllegalArgumentException if any field is invalid
     */
    public void validate() {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be null or empty");
        }
        if (species == null || species.trim().isEmpty()) {
            throw new IllegalArgumentException("Species cannot be null or empty");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
        if (ownerId == null || ownerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner ID cannot be null or empty");
        }
    }
}