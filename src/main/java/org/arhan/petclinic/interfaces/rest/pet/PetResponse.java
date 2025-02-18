package org.arhan.petclinic.interfaces.rest.pet;

import org.arhan.petclinic.application.pet.PetDTO;
import java.time.LocalDate;

/**
 * Response DTO for pet data.
 */
public record PetResponse(
    String id,
    String name,
    String species,
    LocalDate birthDate,
    String ownerId
) {
    /**
     * Creates a response DTO from an application DTO.
     *
     * @param dto the application DTO
     * @return the response DTO
     */
    public static PetResponse fromDTO(PetDTO dto) {
        return new PetResponse(
            dto.id(),
            dto.name(),
            dto.species(),
            dto.birthDate(),
            dto.ownerId()
        );
    }
}