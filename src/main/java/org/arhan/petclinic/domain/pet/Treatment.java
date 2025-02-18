package org.arhan.petclinic.domain.pet;

import java.time.LocalDateTime;

/**
 * Treatment is a value object representing a medical treatment administered to a pet.
 * This is a simplified version that can be expanded later with additional properties.
 */
public record Treatment(String description, LocalDateTime administeredAt) {
    
    public Treatment {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Treatment description cannot be null or empty");
        }
        if (administeredAt == null) {
            throw new IllegalArgumentException("Treatment administration date cannot be null");
        }
        if (administeredAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Treatment administration date cannot be in the future");
        }
        description = description.trim();
    }
}