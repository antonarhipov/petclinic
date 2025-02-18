package org.arhan.petclinic.domain.pet;

import org.arhan.petclinic.domain.owner.OwnerId;
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
     */
    Pet findById(PetId id);

    /**
     * Saves a pet.
     *
     * @param pet the pet to save
     */
    void save(Pet pet);

    /**
     * Finds all pets owned by the given owner.
     *
     * @param ownerId the ID of the owner
     * @return a list of pets owned by the owner
     */
    List<Pet> findByOwner(OwnerId ownerId);
}