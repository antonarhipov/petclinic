package org.arhan.petclinic.domain.owner;

/**
 * ContactInformation is a value object representing all contact details for a person.
 * It ensures that all contact information is properly formatted and valid.
 */
public record ContactInformation(
    String email,
    String phone,
    Address address
) {
    private static final String EMAIL_PATTERN = 
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    
    private static final String PHONE_PATTERN = 
        "^\\+?[1-9]\\d{1,14}$";

    public ContactInformation {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        
        email = email.trim().toLowerCase();
        phone = phone.trim();
        
        if (!email.matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!phone.matches(PHONE_PATTERN)) {
            throw new IllegalArgumentException("Invalid phone format. Use E.164 format (e.g., +1234567890)");
        }
    }
}