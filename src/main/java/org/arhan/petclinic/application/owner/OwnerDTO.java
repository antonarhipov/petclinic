package org.arhan.petclinic.application.owner;

import java.util.List;

/**
 * Data Transfer Object for Owner entity.
 */
public record OwnerDTO(
    String id,
    String firstName,
    String lastName,
    String email,
    String phone,
    AddressDTO address,
    List<String> petIds
) {
    /**
     * Creates a DTO from a domain entity.
     *
     * @param owner the domain entity
     * @return a new DTO instance
     */
    public static OwnerDTO fromDomain(org.arhan.petclinic.domain.owner.Owner owner) {
        return new OwnerDTO(
            owner.getId().value(),
            owner.getName().firstName(),
            owner.getName().lastName(),
            owner.getContactInfo().email(),
            owner.getContactInfo().phone(),
            new AddressDTO(
                owner.getContactInfo().address().street(),
                owner.getContactInfo().address().city(),
                owner.getContactInfo().address().state(),
                owner.getContactInfo().address().postalCode()
            ),
            owner.getPets().stream()
                .map(petId -> petId.value())
                .toList()
        );
    }
}

/**
 * Nested DTO for address information.
 */
record AddressDTO(
    String street,
    String city,
    String state,
    String postalCode
) {}