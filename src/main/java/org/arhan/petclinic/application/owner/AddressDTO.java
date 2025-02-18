package org.arhan.petclinic.application.owner;

import org.arhan.petclinic.domain.owner.Address;

/**
 * DTO for address data in the application layer.
 */
public record AddressDTO(
    String street,
    String city,
    String state,
    String postalCode
) {
    /**
     * Creates a DTO from a domain entity.
     *
     * @param address the domain entity
     * @return the DTO
     */
    public static AddressDTO fromDomain(Address address) {
        return new AddressDTO(
            address.street(),
            address.city(),
            address.state(),
            address.postalCode()
        );
    }
}