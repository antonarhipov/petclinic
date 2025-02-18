package org.arhan.petclinic.domain.owner;

/**
 * Address is a value object representing a physical address.
 * It ensures that all address components are properly formatted.
 */
public record Address(
    String street,
    String city,
    String state,
    String postalCode
) {
    public Address {
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
        
        street = street.trim();
        city = city.trim();
        state = state.trim();
        postalCode = postalCode.trim();
    }

    /**
     * Returns the full address in a formatted string.
     *
     * @return the formatted address
     */
    public String getFormattedAddress() {
        return String.format("%s, %s, %s %s", street, city, state, postalCode);
    }
}