package org.arhan.petclinic.infrastructure.persistence.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Spring Data JPA repository for Pet entities.
 */
interface PetJpaRepository extends JpaRepository<PetJpaEntity, String> {
    
    /**
     * Finds all pets owned by the given owner.
     *
     * @param ownerId the ID of the owner
     * @return list of pets owned by the owner
     */
    List<PetJpaEntity> findByOwnerId(String ownerId);
}