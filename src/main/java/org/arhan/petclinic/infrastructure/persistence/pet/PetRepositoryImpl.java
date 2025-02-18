package org.arhan.petclinic.infrastructure.persistence.pet;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.arhan.petclinic.domain.owner.OwnerId;
import org.arhan.petclinic.domain.pet.Pet;
import org.arhan.petclinic.domain.pet.PetId;
import org.arhan.petclinic.domain.pet.PetRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * JPA implementation of PetRepository.
 */
@Repository
public class PetRepositoryImpl implements PetRepository {
    
    private final PetJpaRepository jpaRepository;

    public PetRepositoryImpl(PetJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Pet findById(PetId id) {
        if (id == null) {
            throw new IllegalArgumentException("Pet ID cannot be null");
        }
        return jpaRepository.findById(id.value())
            .map(PetJpaEntity::toDomain)
            .orElseThrow(() -> EntityNotFoundException.withId("Pet", id.value()));
    }

    @Override
    public void save(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        var entity = PetJpaEntity.fromDomain(pet);
        jpaRepository.save(entity);
    }

    @Override
    public List<Pet> findByOwner(OwnerId ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        return jpaRepository.findByOwnerId(ownerId.value())
            .stream()
            .map(PetJpaEntity::toDomain)
            .toList();
    }
}