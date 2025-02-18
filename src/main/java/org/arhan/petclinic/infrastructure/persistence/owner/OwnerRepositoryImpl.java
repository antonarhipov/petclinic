package org.arhan.petclinic.infrastructure.persistence.owner;

import org.arhan.petclinic.domain.common.EntityNotFoundException;
import org.arhan.petclinic.domain.owner.Owner;
import org.arhan.petclinic.domain.owner.OwnerId;
import org.arhan.petclinic.domain.owner.OwnerRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * JPA implementation of OwnerRepository.
 */
@Repository
public class OwnerRepositoryImpl implements OwnerRepository {
    
    private final OwnerJpaRepository jpaRepository;

    public OwnerRepositoryImpl(OwnerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Owner findById(OwnerId id) {
        if (id == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        return jpaRepository.findById(id.value())
            .map(OwnerJpaEntity::toDomain)
            .orElseThrow(() -> EntityNotFoundException.withId("Owner", id.value()));
    }

    @Override
    public void save(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }
        var entity = OwnerJpaEntity.fromDomain(owner);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Owner> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return jpaRepository.findByEmail(email.trim().toLowerCase())
            .map(OwnerJpaEntity::toDomain);
    }
}