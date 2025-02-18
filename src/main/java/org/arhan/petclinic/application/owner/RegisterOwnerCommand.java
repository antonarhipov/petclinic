package org.arhan.petclinic.application.owner;

/**
 * Command object for registering a new owner.
 */
public record RegisterOwnerCommand(
    String firstName,
    String lastName,
    String email,
    String phone,
    String street,
    String city,
    String state,
    String postalCode
) {
    private static final String EMAIL_PATTERN = 
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    
    private static final String PHONE_PATTERN = 
        "^\\+?[1-9]\\d{1,14}$";

    /**
     * Validates the command data.
     *
     * @throws IllegalArgumentException if any field is invalid
     */
    public void validate() {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.trim().matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        if (!phone.trim().matches(PHONE_PATTERN)) {
            throw new IllegalArgumentException("Invalid phone format. Use E.164 format (e.g., +1234567890)");
        }
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }
        if (postalCode == null || postalCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be null or empty");
        }
    }
}