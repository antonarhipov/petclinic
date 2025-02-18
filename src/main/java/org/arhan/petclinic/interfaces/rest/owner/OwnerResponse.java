package org.arhan.petclinic.interfaces.rest.owner;

import org.arhan.petclinic.application.owner.OwnerDTO;
import java.util.List;

/**
 * Response DTO for owner data.
 */
public record OwnerResponse(
    String id,
    String firstName,
    String lastName,
    String email,
    String phone,
    AddressResponse address,
    List<String> petIds
) {
    /**
     * Creates a response DTO from an application DTO.
     *
     * @param dto the application DTO
     * @return the response DTO
     */
    public static OwnerResponse fromDTO(OwnerDTO dto) {
        return new OwnerResponse(
            dto.id(),
            dto.firstName(),
            dto.lastName(),
            dto.email(),
            dto.phone(),
            new AddressResponse(
                dto.address().street(),
                dto.address().city(),
                dto.address().state(),
                dto.address().postalCode()
            ),
            dto.petIds()
        );
    }
}

/**
 * Nested response DTO for address data.
 */
record AddressResponse(
    String street,
    String city,
    String state,
    String postalCode
) {}