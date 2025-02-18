package org.arhan.petclinic.domain.owner;

/**
 * FullName is a value object representing a person's full name.
 * It ensures that both first name and last name are properly formatted.
 */
public record FullName(String firstName, String lastName) {
    
    public FullName {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        
        firstName = firstName.trim();
        lastName = lastName.trim();
    }

    /**
     * Returns the full name in "FirstName LastName" format.
     *
     * @return the formatted full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}