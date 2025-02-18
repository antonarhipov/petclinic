package org.arhan.petclinic.domain.owner;

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
     */
    Owner findById(OwnerId id);

    /**
     * Saves an owner.
     *
     * @param owner the owner to save
     */
    void save(Owner owner);

    /**
     * Finds an owner by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the owner if found, or empty if not found
     */
    Optional<Owner> findByEmail(String email);
}