package org.arhan.petclinic.domain.pet;

/**
 * PetName is a value object representing the name of a pet.
 * It ensures that the name is never null or empty, is properly trimmed,
 * and doesn't exceed the maximum allowed length of 50 characters.
 */
public record PetName(String value) {
    private static final int MAX_LENGTH = 50;

    public PetName {
        if (value == null) {
            throw new IllegalArgumentException("Pet name cannot be null");
        }
        value = value.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be empty");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Pet name cannot exceed " + MAX_LENGTH + " characters");
        }
    }
}