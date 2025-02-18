package org.arhan.petclinic.domain.owner;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import java.util.Optional;

/**
 * Repository interface for managing Owner entities.
 */
public interface OwnerRepository {
    /**
     * Finds an owner by their ID.
     *
     * @param id the ID of the owner to find
     * @return the owner with the given ID
     * @throws EntityNotFoundException if no owner is found with the given ID
     * @throws IllegalArgumentException if id is null
     */
    Owner findById(OwnerId id);

    /**
     * Saves an owner.
     *
     * @param owner the owner to save
     * @throws IllegalArgumentException if owner is null
     */
    void save(Owner owner);

    /**
     * Finds an owner by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the owner if found, or empty if not found
     * @throws IllegalArgumentException if email is null or empty
     */
    Optional<Owner> findByEmail(String email);
}