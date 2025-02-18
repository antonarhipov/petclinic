package org.arhan.petclinic.interfaces.rest.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

/**
 * Request DTO for updating an existing pet.
 */
public record UpdatePetRequest(
    @NotBlank(message = "Pet ID is required")
    String id,

    @NotBlank(message = "Pet name is required")
    String name,

    @NotBlank(message = "Species is required")
    String species,

    @NotNull(message = "Birth date is required")
    @PastOrPresent(message = "Birth date cannot be in the future")
    LocalDate birthDate,

    @NotBlank(message = "Owner ID is required")
    String ownerId
) {
    /**
     * Converts this request to a command object.
     *
     * @return the command object
     */
    public org.arhan.petclinic.application.pet.UpdatePetCommand toCommand() {
        return new org.arhan.petclinic.application.pet.UpdatePetCommand(
            id,
            name,
            species,
            birthDate,
            ownerId
        );
    }
}