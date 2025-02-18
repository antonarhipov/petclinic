package org.arhan.petclinic.application.owner;

import java.util.Optional;

/**
 * Application service for managing owners.
 */
public interface OwnerService {
    
    /**
     * Registers a new owner.
     *
     * @param command the registration command
     * @return the registered owner
     * @throws IllegalArgumentException if the command is invalid
     */
    OwnerDTO registerOwner(RegisterOwnerCommand command);

    /**
     * Updates an existing owner.
     *
     * @param command the update command
     * @return the updated owner
     * @throws IllegalArgumentException if the command is invalid
     * @throws org.arhan.petclinic.domain.common.EntityNotFoundException if the owner is not found
     */
    OwnerDTO updateOwner(UpdateOwnerCommand command);

    /**
     * Finds an owner by their ID.
     *
     * @param id the ID of the owner to find
     * @return the owner
     * @throws IllegalArgumentException if id is null or empty
     * @throws org.arhan.petclinic.domain.common.EntityNotFoundException if the owner is not found
     */
    OwnerDTO findById(String id);

    /**
     * Finds an owner by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the owner if found, or empty if not found
     * @throws IllegalArgumentException if email is null or empty
     */
    Optional<OwnerDTO> findByEmail(String email);
}