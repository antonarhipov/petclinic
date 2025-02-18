package org.arhan.petclinic.domain.pet;

/**
 * Species is a value object representing the type of pet (e.g., "Dog", "Cat", etc.).
 * It ensures that the species name is never null or empty and is properly trimmed.
 */
public record Species(String name) {
    public Species {
        if (name == null) {
            throw new IllegalArgumentException("Species name cannot be null");
        }
        name = name.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Species name cannot be empty");
        }
    }
}