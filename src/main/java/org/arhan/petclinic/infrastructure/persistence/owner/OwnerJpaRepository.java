package org.arhan.petclinic.infrastructure.persistence.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Spring Data JPA repository for Owner entities.
 */
public interface OwnerJpaRepository extends JpaRepository<OwnerJpaEntity, String> {
    
    /**
     * Finds an owner by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the owner if found, or empty if not found
     */
    Optional<OwnerJpaEntity> findByEmail(String email);
}