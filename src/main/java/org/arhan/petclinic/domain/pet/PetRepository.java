package org.arhan.petclinic.domain.pet;

import org.arhan.petclinic.domain.owner.OwnerId;
import org.arhan.petclinic.domain.common.EntityNotFoundException;
import java.util.List;

/**
 * Repository interface for managing Pet entities.
 */
public interface PetRepository {
    /**
     * Finds a pet by its ID.
     *
     * @param id the ID of the pet to find
     * @return the pet with the given ID
     * @throws EntityNotFoundException if no pet is found with the given ID
     */
    Pet findById(PetId id);

    /**
     * Saves a pet.
     *
     * @param pet the pet to save
     * @throws IllegalArgumentException if pet is null
     */
    void save(Pet pet);

    /**
     * Finds all pets owned by the given owner.
     *
     * @param ownerId the ID of the owner
     * @return a list of pets owned by the owner, empty list if no pets are found
     * @throws IllegalArgumentException if ownerId is null
     */
    List<Pet> findByOwner(OwnerId ownerId);
}