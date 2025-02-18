package org.arhan.petclinic.application.owner;

import org.arhan.petclinic.domain.owner.Owner;
import java.util.List;

/**
 * DTO for owner data in the application layer.
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
     * @return the DTO
     */
    public static OwnerDTO fromDomain(Owner owner) {
        return new OwnerDTO(
            owner.getId().value(),
            owner.getName().firstName(),
            owner.getName().lastName(),
            owner.getContactInfo().email(),
            owner.getContactInfo().phone(),
            AddressDTO.fromDomain(owner.getContactInfo().address()),
            owner.getPets().stream()
                .map(petId -> petId.value())
                .toList()
        );
    }
}

