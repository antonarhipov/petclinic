package org.arhan.petclinic.application.pet;

import java.time.LocalDate;

/**
 * Data Transfer Object for Pet entity.
 */
public record PetDTO(
    String id,
    String name,
    String species,
    LocalDate birthDate,
    String ownerId
) {
    /**
     * Creates a DTO from a domain entity.
     *
     * @param pet the domain entity
     * @return a new DTO instance
     */
    public static PetDTO fromDomain(org.arhan.petclinic.domain.pet.Pet pet) {
        return new PetDTO(
            pet.getId().value(),
            pet.getName().value(),
            pet.getSpecies().name(),
            pet.getBirthDate(),
            pet.getOwnerId().value()
        );
    }
}