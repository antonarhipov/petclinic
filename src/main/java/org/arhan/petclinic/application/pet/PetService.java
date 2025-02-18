package org.arhan.petclinic.application.pet;

import java.util.List;

/**
 * Application service for managing pets.
 */
public interface PetService {
    
    /**
     * Registers a new pet.
     *
     * @param command the registration command
     * @return the registered pet
     * @throws IllegalArgumentException if the command is invalid
     * @throws org.arhan.petclinic.domain.common.EntityNotFoundException if the owner is not found
     */
    PetDTO registerPet(RegisterPetCommand command);

    /**
     * Updates an existing pet.
     *
     * @param command the update command
     * @return the updated pet
     * @throws IllegalArgumentException if the command is invalid
     * @throws org.arhan.petclinic.domain.common.EntityNotFoundException if the pet or owner is not found
     */
    PetDTO updatePet(UpdatePetCommand command);

    /**
     * Finds a pet by its ID.
     *
     * @param id the ID of the pet to find
     * @return the pet
     * @throws IllegalArgumentException if id is null or empty
     * @throws org.arhan.petclinic.domain.common.EntityNotFoundException if the pet is not found
     */
    PetDTO findById(String id);

    /**
     * Finds all pets owned by the given owner.
     *
     * @param ownerId the ID of the owner
     * @return list of pets owned by the owner
     * @throws IllegalArgumentException if ownerId is null or empty
     * @throws org.arhan.petclinic.domain.common.EntityNotFoundException if the owner is not found
     */
    List<PetDTO> findByOwner(String ownerId);
}